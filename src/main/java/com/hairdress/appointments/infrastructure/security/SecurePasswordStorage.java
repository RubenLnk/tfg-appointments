package com.hairdress.appointments.infrastructure.security;

import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SecurePasswordStorage {

  public static String getEncryptedPassword(String password, String salt) throws Exception {
    String algorithm = "PBKDF2WithHmacSHA1";
    int derivedKeyLength = 160; // for SHA1
    int iterations = 20000; // NIST specifies 10000

    byte[] saltBytes = Base64.getDecoder().decode(salt);
    KeySpec spec = new PBEKeySpec(password.toCharArray(), saltBytes, iterations, derivedKeyLength);
    SecretKeyFactory factory = SecretKeyFactory.getInstance(algorithm);

    byte[] encBytes = factory.generateSecret(spec).getEncoded();
    return Base64.getEncoder().encodeToString(encBytes);
  }

  public static String getNewSalt() throws Exception {
    // Don't use Random!
    SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
    // NIST recommends minimum 4 bytes. We use 8.
    byte[] salt = new byte[8];
    random.nextBytes(salt);
    return Base64.getEncoder().encodeToString(salt);
  }
}
