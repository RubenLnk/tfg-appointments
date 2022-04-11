package com.hairdress.appointments.infrastructure.rest.spring.controller.mapper;

import com.hairdress.appointments.infrastructure.bbdd.models.Appointment;
import com.hairdress.appointments.infrastructure.bbdd.models.Professional;
import com.hairdress.appointments.infrastructure.rest.spring.controller.request.CreateAppointmentRequestDto;
import com.hairdress.appointments.infrastructure.rest.spring.controller.request.UpdateAppointmentRequestDto;
import com.hairdress.appointments.infrastructure.rest.spring.controller.response.AppointmentResponseDto;
import java.sql.Timestamp;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
    uses = {CustomerMapper.class, ProfessionalMapper.class, HairServiceMapper.class})
public interface AppointmentMapper {

    AppointmentResponseDto toDto(Appointment source);

    @Mapping(target = "customer.id", source = "customerId")
    @Mapping(target = "creatorProfessional", source = "source", qualifiedByName = "mapProfessionalId")
    @Mapping(target = "active", constant = "true")
    @Mapping(target = "creationDate", expression = "java(timestampNow())")
    @Mapping(target = "modificationDate", expression = "java(timestampNow())")
    @Mapping(target = "services", ignore = true)
    Appointment createAppointmentRequestToEntity(CreateAppointmentRequestDto source);

    @Mapping(target = "services", ignore = true)
    Appointment updateAppointmentRequestToEntity(UpdateAppointmentRequestDto source);

    default Timestamp timestampNow() {
        return new Timestamp(System.currentTimeMillis());
    }

    default Appointment updateAppointmentData(Appointment source, Appointment target) {
        target.setAppointmentInitDate(source.getAppointmentInitDate());
        target.setAppointmentEndDate(source.getAppointmentEndDate());
        target.setPrice(source.getPrice());
        target.setObservations(source.getObservations());

        return target;
    }

    @Named("mapProfessionalId")
    default Professional mapProfessionalId(CreateAppointmentRequestDto source) {

        if (source == null || source.getCreatorProfessionalId() == null) {
            return null;
        }

        Professional professional = new Professional();
        professional.setId(source.getCreatorProfessionalId());
        return professional;
    }

}
