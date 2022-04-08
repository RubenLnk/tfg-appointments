package com.hairdress.appointments.infrastructure.security;

import com.hairdress.appointments.infrastructure.error.exception.SecurePasswordException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SecurePasswordStorage {

  private SecurePasswordStorage() {
    // private constructor for static class
  }

  public static String getEncryptedPassword(String password, String salt) {

    try {
      String algorithm = "PBKDF2WithHmacSHA1";
      int derivedKeyLength = 160; // for SHA1
      int iterations = 20000; // NIST specifies 10000

      byte[] saltBytes = Base64.getDecoder().decode(salt);
      KeySpec spec = new PBEKeySpec(password.toCharArray(), saltBytes, iterations, derivedKeyLength);

      SecretKeyFactory factory = SecretKeyFactory.getInstance(algorithm);

      byte[] encBytes = factory.generateSecret(spec).getEncoded();
      return Base64.getEncoder().encodeToString(encBytes);
    } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
      log.error("Error al generar contraseña cifrada", e);
      throw new SecurePasswordException(e.getMessage(), e);
    }
  }

  public static String getNewSalt() {
    try {
      // Don't use Random!
      SecureRandom random = SecureRandom.getInstance("SHA1PRNG");

      // NIST recommends minimum 4 bytes. We use 8.
      byte[] salt = new byte[8];
      random.nextBytes(salt);
      return Base64.getEncoder().encodeToString(salt);
    } catch (NoSuchAlgorithmException e) {
      log.error("Error al generar el salt", e);
      throw new SecurePasswordException(e.getMessage(), e);
    }
  }
}
