package com.hairdress.appointments.infrastructure.service;

import com.hairdress.appointments.infrastructure.bbdd.models.Appointment;
import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentService {

    List<Appointment> findAll();

    Appointment findById(Long id);

    List<Appointment> findAllAppointmentsInADay(LocalDateTime day);

    Appointment save(Appointment appointmentToSave, List<Long> services);

    void delete(Long id);

    Appointment update(Long id, Appointment appointmentToUpdate, List<Long> services);

    Appointment cancel(Long id);
}
