package com.hairdress.appointments.infrastructure.bbdd.repositories;

import com.hairdress.appointments.infrastructure.bbdd.models.HairService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HairServiceRepository extends JpaRepository<HairService, Long> {

}
