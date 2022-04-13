package com.hairdress.appointments.infrastructure.service;

import com.hairdress.appointments.infrastructure.bbdd.models.HairService;
import java.util.List;

public interface HairServiceService {

    HairService findById(Long id);

    List<HairService> findAll();

    List<HairService> findAllActives();

    HairService save(HairService serviceToSave);

    HairService update(Long id, HairService serviceToUpdate);

    HairService cancel(Long id);

    HairService activate(Long id);
}
