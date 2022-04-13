package com.hairdress.appointments.infrastructure.rest.spring.controller.mapper;

import java.sql.Timestamp;

public interface GenericMapper {

    default Timestamp timestampNow() {
        return new Timestamp(System.currentTimeMillis());
    }

}
