package com.hairdress.appointments.infrastructure.service;

import com.hairdress.appointments.infrastructure.bbdd.models.Professional;
import java.util.List;

public interface ProfessionalService {

  Professional findById(Long id);

  List<Professional> findAll();

  Professional signUp(Professional professionalToSignUp);

  void login(String uid, String password);

  void changePassword(String uid, String oldPassword, String newPassword);

  Professional update(Long id, Professional updateProfessional);

  void delete(Long id);
}
