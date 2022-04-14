package com.hairdress.appointments.infrastructure.bbdd.repositories;

import com.hairdress.appointments.infrastructure.bbdd.models.Appointment;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findByActiveTrueAndAppointmentInitDateBetween(
        Timestamp initDate, Timestamp endDate);

    List<Appointment> findByActiveTrueAndCustomerId(Long id);

  @Query("SELECT a FROM Appointment a WHERE a.active = true "
      + "AND ((:initDate BETWEEN a.appointmentInitDate AND a.appointmentEndDate) "
      + "OR (:endDate BETWEEN a.appointmentInitDate AND a.appointmentEndDate)"
      + "OR (a.appointmentInitDate BETWEEN :initDate AND :endDate "
      + "AND a.appointmentEndDate BETWEEN :initDate AND :endDate))")
  List<Appointment> findAppointmentsWithConflictingDates(
      @Param("initDate") Timestamp initDate, @Param("endDate") Timestamp endDate);
}
