package com.hairdress.appointments.infrastructure.rest.spring.controller.mapper;

import com.hairdress.appointments.infrastructure.bbdd.models.Professional;
import com.hairdress.appointments.infrastructure.rest.spring.controller.response.ProfessionalResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfessionalMapper {

  ProfessionalResponseDto toDto(Professional source);
}
