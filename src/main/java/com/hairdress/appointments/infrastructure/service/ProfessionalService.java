package com.hairdress.appointments.infrastructure.service;

import com.hairdress.appointments.infrastructure.bbdd.models.Professional;
import java.util.List;

public interface ProfessionalService {

  Professional findById(Long id);

  List<Professional> findAll();
}
