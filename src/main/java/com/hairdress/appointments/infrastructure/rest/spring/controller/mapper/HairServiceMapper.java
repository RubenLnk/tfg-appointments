package com.hairdress.appointments.infrastructure.rest.spring.controller.mapper;

import com.hairdress.appointments.infrastructure.bbdd.models.HairService;
import com.hairdress.appointments.infrastructure.rest.spring.controller.request.SaveHairServiceRequestDto;
import com.hairdress.appointments.infrastructure.rest.spring.controller.response.HairServiceResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface HairServiceMapper extends GenericMapper {

    HairServiceResponseDto toDto(HairService source);

    @Mapping(target = "active", constant = "true")
    @Mapping(target = "creationDate", expression = "java(timestampNow())")
    @Mapping(target = "modificationDate", expression = "java(timestampNow())")
    HairService createHairServiceRequestToEntity(SaveHairServiceRequestDto source);

    HairService updateHairServiceRequestToEntity(SaveHairServiceRequestDto source);

    default HairService updateHairServiceData(HairService source, HairService target) {
        target.setId(source.getId());
        target.setActive(source.getActive());
        target.setCancellationDate(source.getCancellationDate());
        target.setCreationDate(source.getCreationDate());
        target.setModificationDate(source.getModificationDate());

        return target;
    }

}
