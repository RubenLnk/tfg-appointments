package com.hairdress.appointments.infrastructure.rest.spring.controller.mapper;

import com.hairdress.appointments.infrastructure.bbdd.models.Professional;
import com.hairdress.appointments.infrastructure.rest.spring.controller.request.SignUpProfessionalRequestDto;
import com.hairdress.appointments.infrastructure.rest.spring.controller.request.UpdateProfessionalRequestDto;
import com.hairdress.appointments.infrastructure.rest.spring.controller.response.ProfessionalResponseDto;
import java.sql.Timestamp;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProfessionalMapper {

  ProfessionalResponseDto toDto(Professional source);

  @Mapping(target = "creationDate", expression = "java(timestampNow())")
  @Mapping(target = "modificationDate", expression = "java(timestampNow())")
  Professional signUpRequestToEntity(SignUpProfessionalRequestDto source);

  Professional updateProfessionalRequestToEntity(UpdateProfessionalRequestDto source);

  default Professional updateProfessionalData(Professional source, Professional target) {
    target.setName(source.getName());
    target.setSurname1(source.getSurname1());
    target.setSurname2(source.getSurname2());
    target.setModificationDate(timestampNow());

    return target;
  }

  default Timestamp timestampNow() {
    return new Timestamp(System.currentTimeMillis());
  }
}
