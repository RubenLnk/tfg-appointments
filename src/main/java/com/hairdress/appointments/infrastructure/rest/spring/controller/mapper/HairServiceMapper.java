package com.hairdress.appointments.infrastructure.rest.spring.controller.mapper;

import com.hairdress.appointments.infrastructure.bbdd.models.HairService;
import com.hairdress.appointments.infrastructure.rest.spring.controller.response.HairServiceResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface HairServiceMapper {

    HairServiceResponseDto toDto(HairService source);

}
