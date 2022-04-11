package com.hairdress.appointments.infrastructure.service.impl;

import com.hairdress.appointments.infrastructure.bbdd.models.Appointment;
import com.hairdress.appointments.infrastructure.bbdd.models.Customer;
import com.hairdress.appointments.infrastructure.bbdd.models.HairService;
import com.hairdress.appointments.infrastructure.bbdd.models.Professional;
import com.hairdress.appointments.infrastructure.bbdd.repositories.AppointmentRepository;
import com.hairdress.appointments.infrastructure.error.exception.BadRequestException;
import com.hairdress.appointments.infrastructure.error.exception.ModelNotFoundException;
import com.hairdress.appointments.infrastructure.rest.spring.controller.mapper.AppointmentMapper;
import com.hairdress.appointments.infrastructure.service.AppointmentService;
import com.hairdress.appointments.infrastructure.service.CustomerService;
import com.hairdress.appointments.infrastructure.service.HairServiceService;
import com.hairdress.appointments.infrastructure.service.ProfessionalService;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class AppointmentServiceImpl implements AppointmentService {

    public static final String APPOINTMENT_ID_NOT_FOUND = "No se pudo encontrar en la BD la "
        + "cita con id: {}";
    public static final String APPOINTMENT_ID_NOT_FOUND_EXCEPTION_TEXT = "No se pudo encontrar la "
        + "cita con id: ";

    private final AppointmentRepository repository;
    private final AppointmentMapper mapper;
    private final CustomerService customerService;
    private final ProfessionalService professionalService;
    private final HairServiceService hairServiceService;

    @Override
    public List<Appointment> findAll() {
        return repository.findAll();
    }

    @Override
    public Appointment findById(Long id) {
        Optional<Appointment> opt = repository.findById(id);

        if (opt.isEmpty()) {
            log.error(APPOINTMENT_ID_NOT_FOUND, id);
            throw new ModelNotFoundException(APPOINTMENT_ID_NOT_FOUND_EXCEPTION_TEXT + id);
        }

        return opt.get();
    }

    @Override
    public List<Appointment> findAllAppointmentsInADay(LocalDateTime day) {

        LocalDateTime initDate = day.truncatedTo(ChronoUnit.DAYS);

        LocalDateTime endDate = initDate.plusDays(1);

        return repository.findByAppointmentInitDateBetween(Timestamp.valueOf(initDate),
            Timestamp.valueOf(endDate));
    }

    @Override
    public Appointment save(Appointment appointmentToSave, List<Long> services) {

        checkConflictingDates(appointmentToSave);

        // Get the customer
        Customer customer = customerService.findById(appointmentToSave.getCustomer().getId());
        appointmentToSave.setCustomer(customer);

        // Get the creator professional
        if (appointmentToSave.getCreatorProfessional() != null
            && appointmentToSave.getCreatorProfessional().getId() != null) {
            Professional professional =
              professionalService.findById(appointmentToSave.getCreatorProfessional().getId());
            appointmentToSave.setCreatorProfessional(professional);
        }

        setServiceList(appointmentToSave, services);

        return repository.save(appointmentToSave);
    }

    @Override
    public void delete(Long id) {
        repository.delete(findById(id));
    }

    @Override
    public Appointment update(Long id, Appointment appointmentToUpdate,
        List<Long> services) {
        Appointment bdAppointment = findById(id);

        Appointment appointmentToSave = mapper.updateAppointmentData(
            appointmentToUpdate, bdAppointment);

        setServiceList(appointmentToSave, services);

        return repository.save(appointmentToSave);
    }

    private void checkConflictingDates(Appointment appointmentToSave) {
        var appointmentsInSameDay = repository
            .findAppointmentsWithConflictingDates(
                appointmentToSave.getAppointmentInitDate(),
                appointmentToSave.getAppointmentEndDate());
        if (!appointmentsInSameDay.isEmpty()) {
            IntStream.range(0, appointmentsInSameDay.size()).forEach(idx ->
                log.error("Las fechas de la cita que se está intentando crear entra en conflicto "
                    + "con la cita con id: {}", appointmentsInSameDay.get(idx).getId()));
            throw new BadRequestException("No se puede crear la cita en la fecha escogida. "
                + "Inténtelo de nuevo con otra fecha");
        }
    }


    private void setServiceList(Appointment appointmentToSave, List<Long> services) {
        var serviceList = new ArrayList<HairService>();
        services.forEach(serviceId -> serviceList.add(hairServiceService.findById(serviceId)));
        appointmentToSave.setServices(serviceList);
    }

}
