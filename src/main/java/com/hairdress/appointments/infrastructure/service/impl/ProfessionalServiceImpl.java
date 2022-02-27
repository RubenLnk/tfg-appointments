package com.hairdress.appointments.infrastructure.service.impl;

import com.hairdress.appointments.infrastructure.bbdd.models.Professional;
import com.hairdress.appointments.infrastructure.bbdd.repositories.ProfessionalRepository;
import com.hairdress.appointments.infrastructure.error.exception.AuthorizationException;
import com.hairdress.appointments.infrastructure.error.exception.GenericException;
import com.hairdress.appointments.infrastructure.error.exception.ModelNotFoundException;
import com.hairdress.appointments.infrastructure.error.exception.UserAlreadyExistsException;
import com.hairdress.appointments.infrastructure.security.SecurePasswordStorage;
import com.hairdress.appointments.infrastructure.service.ProfessionalService;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProfessionalServiceImpl implements ProfessionalService {

  private final ProfessionalRepository repository;

  @Override
  public Professional findById(Long id) {
    Optional<Professional> opt = repository.findById(id);

    if (opt.isEmpty()) {
      log.error("No se pudo encontrar en la BD el profesional con id: {}", id);
      throw new ModelNotFoundException("No se pudo encontrar al profesional con id: " + id);
    }

    return opt.get();
  }

  @Override
  public List<Professional> findAll() {
    return repository.findAll();
  }

  @Override
  @Transactional
  public Professional signUp(Professional professionalToSignUp) {

    Optional<Professional> opt = repository.findByUid(professionalToSignUp.getUid());

    if (opt.isPresent()) {
      log.error("El usuario {} ya existe", professionalToSignUp.getUid());
      throw new UserAlreadyExistsException("El usuario ya existe");
    }

    try {
      String salt = SecurePasswordStorage.getNewSalt();
      String encryptedPassword = SecurePasswordStorage.getEncryptedPassword(
          professionalToSignUp.getPassword(), salt);

      professionalToSignUp.setSalt(salt);
      professionalToSignUp.setPassword(encryptedPassword);
    } catch (Exception e) {
      log.error("Se ha producido un error al crear la contraseña del profesional", e);
      throw new GenericException("Se ha producido un error inesperado al dar de alta al profesional",
          e.getCause());
    }

    return repository.save(professionalToSignUp);
  }

  @Override
  public void login(String uid, String password) {

    Optional<Professional> opt = repository.findByUid(uid);

    if (opt.isEmpty()) {
      log.error("El usuario {} no existe", uid);
      throw new AuthorizationException("El usuario o la contraseña proporcionados no son correctos");
    }

    Professional professional = opt.get();

    String encryptedPassword;

    try {
      encryptedPassword = SecurePasswordStorage.getEncryptedPassword(
          password, professional.getSalt());
    } catch (Exception e) {
      log.error("Se ha producido un error al comprobrar la contraseña del profesional", e);
      throw new GenericException("Se ha producido un error inesperado al autenticar al profesional",
          e.getCause());
    }

    if (!encryptedPassword.equals(professional.getPassword())) {
      throw new AuthorizationException("El usuario o la contraseña proporcionados no son correctos");
    }
  }

  @Override
  @Transactional
  public void changePassword(String uid, String oldPassword, String newPassword) {
    Optional<Professional> opt = repository.findByUid(uid);

    if (opt.isEmpty()) {
      log.error("El usuario {} no existe", uid);
      throw new AuthorizationException("El uid introducido no es correcto");
    }

    Professional professional = opt.get();

    String oldEncryptedPassword;

    // Obtenemos la contraseña antigua cifrada
    try {
      oldEncryptedPassword = SecurePasswordStorage.getEncryptedPassword(
          oldPassword, professional.getSalt());
    } catch (Exception e) {
      log.error("Se ha producido un error al comprobrar la contraseña antigua del profesional", e);
      throw new GenericException("Se ha producido un error inesperado al cambiar la contraseña",
          e.getCause());
    }

    // Si la contraseña que ha introducido el usuario no es la misma, devolvemos una excepcion
    if (!oldEncryptedPassword.equals(professional.getPassword())) {
      log.error("La contraseña introducida no coincide con la contraseña del profesional");
      throw new AuthorizationException("La contraseña actual no corresponde con la contraseña"
          + " del profesional");
    }

    // Creamos un salt nuevo y ciframos la nueva contraseña, y guardamos el profesional
    try {
      String newSalt = SecurePasswordStorage.getNewSalt();
      String newEncryptedPassword = SecurePasswordStorage.getEncryptedPassword(
          newPassword, newSalt);

      professional.setSalt(newSalt);
      professional.setPassword(newEncryptedPassword);
      professional.setModificationDate(new Timestamp(System.currentTimeMillis()));
    } catch (Exception e) {
      log.error("Se ha producido un error al generar la nueva contraseña del profesional", e);
      throw new GenericException("Se ha producido un error inesperado al cambiar la contraseña",
          e.getCause());
    }

    repository.save(professional);
  }
}
