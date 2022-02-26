package com.hairdress.appointments.infrastructure.service.impl;

import com.hairdress.appointments.infrastructure.bbdd.models.Professional;
import com.hairdress.appointments.infrastructure.bbdd.repositories.ProfessionalRepository;
import com.hairdress.appointments.infrastructure.error.exception.AuthorizationException;
import com.hairdress.appointments.infrastructure.error.exception.GenericException;
import com.hairdress.appointments.infrastructure.error.exception.ModelNotFoundException;
import com.hairdress.appointments.infrastructure.error.exception.UserAlreadyExistsException;
import com.hairdress.appointments.infrastructure.security.SecurePasswordStorage;
import com.hairdress.appointments.infrastructure.service.ProfessionalService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProfessionalServiceImpl implements ProfessionalService {

  private final ProfessionalRepository repository;

  @Override
  public Professional findById(Long id) {
    Optional<Professional> opt = repository.findById(id);

    if (opt.isEmpty()) {
      throw new ModelNotFoundException("No se pudo encontrar al profesional con id: " + id);
    }

    return opt.get();
  }

  @Override
  public List<Professional> findAll() {
    return repository.findAll();
  }

  @Override
  public Professional signUp(Professional professionalToSignUp) {

    Optional<Professional> opt = repository.findByUid(professionalToSignUp.getUid());

    if (opt.isPresent()) {
      throw new UserAlreadyExistsException("El usuario ya existe");
    }

    try {
      String salt = SecurePasswordStorage.getNewSalt();
      String encryptedPassword = SecurePasswordStorage.getEncryptedPassword(
          professionalToSignUp.getPassword(), salt);

      professionalToSignUp.setSalt(salt);
      professionalToSignUp.setPassword(encryptedPassword);
    } catch (Exception e) {
      throw new GenericException("Se ha producido un error inesperado al dar de alta al profesional",
          e.getCause());
    }

    return repository.save(professionalToSignUp);
  }

  @Override
  public void login(String uid, String password) {

    Optional<Professional> opt = repository.findByUid(uid);

    if (opt.isEmpty()) {
      throw new AuthorizationException("El usuario o la contraseña proporcionados no son correctos");
    }

    Professional professional = opt.get();

    String encryptedPassword;

    try {
      encryptedPassword = SecurePasswordStorage.getEncryptedPassword(
          password, professional.getSalt());
    } catch (Exception e) {
      throw new GenericException("Se ha producido un error inesperado al autenticar al profesional",
          e.getCause());
    }

    if (!encryptedPassword.equals(professional.getPassword())) {
      throw new AuthorizationException("El usuario o la contraseña proporcionados no son correctos");
    }
  }
}
