package com.hairdress.appointments.infrastructure.service.impl;

import com.hairdress.appointments.infrastructure.bbdd.models.Professional;
import com.hairdress.appointments.infrastructure.bbdd.repositories.ProfessionalRepository;
import com.hairdress.appointments.infrastructure.error.exception.ModelNotFoundException;
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
      throw new ModelNotFoundException("Cannot find professional with id: " + id);
    }

    return opt.get();
  }

  @Override
  public List<Professional> findAll() {
    return repository.findAll();
  }
}
