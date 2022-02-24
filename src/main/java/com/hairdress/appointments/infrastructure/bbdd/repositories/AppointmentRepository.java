package com.hairdress.appointments.infrastructure.bbdd.repositories;

import com.hairdress.appointments.infrastructure.bbdd.models.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

}
