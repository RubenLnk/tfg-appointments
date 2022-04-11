package com.hairdress.appointments.infrastructure.service;

import com.hairdress.appointments.infrastructure.bbdd.models.HairService;

public interface HairServiceService {

    HairService findById(Long id);
}
