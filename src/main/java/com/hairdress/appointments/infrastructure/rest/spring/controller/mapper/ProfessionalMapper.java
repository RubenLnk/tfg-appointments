package com.hairdress.appointments.infrastructure.rest.spring.controller.mapper;

import com.hairdress.appointments.infrastructure.bbdd.models.Professional;
import com.hairdress.appointments.infrastructure.rest.spring.controller.request.SignUpProfessionalRequestDto;
import com.hairdress.appointments.infrastructure.rest.spring.controller.response.ProfessionalResponseDto;
import java.sql.Timestamp;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProfessionalMapper {

  ProfessionalResponseDto toDto(Professional source);

  @Mapping(target = "creationDate", expression = "java(timestampNow())")
  @Mapping(target = "modificationDate", expression = "java(timestampNow())")
  Professional signUpRequestToEntity(SignUpProfessionalRequestDto source);

  default Timestamp timestampNow() {
    return new Timestamp(System.currentTimeMillis());
  }
}
