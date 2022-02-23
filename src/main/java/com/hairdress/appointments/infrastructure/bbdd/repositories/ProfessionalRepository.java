package com.hairdress.appointments.infrastructure.bbdd.repositories;

import com.hairdress.appointments.infrastructure.bbdd.models.Professional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessionalRepository extends JpaRepository<Professional, Long> {
}
