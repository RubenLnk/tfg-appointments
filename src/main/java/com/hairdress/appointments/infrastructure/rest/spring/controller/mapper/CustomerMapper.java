package com.hairdress.appointments.infrastructure.rest.spring.controller.mapper;

import com.hairdress.appointments.infrastructure.bbdd.models.Customer;
import com.hairdress.appointments.infrastructure.rest.spring.controller.request.SignUpCustomerRequestDto;
import com.hairdress.appointments.infrastructure.rest.spring.controller.request.UpdateCustomerRequestDto;
import com.hairdress.appointments.infrastructure.rest.spring.controller.response.CustomerResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerMapper extends GenericMapper {

    @Mapping(target = "registered", expression = "java(source.getEmail() != null)")
    @Mapping(target = "firstConnection", expression = "java(source.getEmail() != null)")
    @Mapping(target = "creationDate", expression = "java(timestampNow())")
    @Mapping(target = "modificationDate", expression = "java(timestampNow())")
    Customer signUpCustomerToEntity(SignUpCustomerRequestDto source);

    Customer updateCustomerToEntity(UpdateCustomerRequestDto source);

    CustomerResponseDto toDto(Customer source);

    default Customer updateCustomerData(Customer source, Customer target) {
        target.setName(source.getName());
        target.setSurname1(source.getSurname1());
        target.setSurname2(source.getSurname2());
        target.setPhone(source.getPhone());

        return target;
    }
}
