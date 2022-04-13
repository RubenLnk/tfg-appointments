package com.hairdress.appointments.infrastructure.service.impl;

import com.hairdress.appointments.infrastructure.bbdd.models.Appointment;
import com.hairdress.appointments.infrastructure.bbdd.models.HairService;
import com.hairdress.appointments.infrastructure.bbdd.repositories.HairServiceRepository;
import com.hairdress.appointments.infrastructure.error.exception.ModelNotFoundException;
import com.hairdress.appointments.infrastructure.rest.spring.controller.mapper.HairServiceMapper;
import com.hairdress.appointments.infrastructure.service.HairServiceService;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class HairServiceServiceImpl implements HairServiceService {

    public static final String SERVICE_ID_NOT_FOUND = "No se pudo encontrar en la BD el servicio con id: {}";
    public static final String SERVICE_ID_NOT_FOUND_EXCEPTION_TEXT = "No se pudo encontrar el servicio con id: ";

    private final HairServiceRepository repository;
    private final HairServiceMapper mapper;

    @Override
    public HairService findById(Long id) {

        Optional<HairService> opt = repository.findById(id);

        if (opt.isEmpty()) {
            log.error(SERVICE_ID_NOT_FOUND, id);
            throw new ModelNotFoundException(SERVICE_ID_NOT_FOUND_EXCEPTION_TEXT + id);
        }

        return opt.get();
    }

    @Override
    public List<HairService> findAll() {
        return repository.findAll();
    }

    @Override
    public List<HairService> findAllActives() {
        return repository.findByActiveTrue();
    }

    @Override
    public HairService save(HairService serviceToSave) {
        return repository.save(serviceToSave);
    }

    @Override
    public HairService update(Long id, HairService serviceToUpdate) {

        var hairService = findById(id);

        var serviceToSave = mapper.updateHairServiceData(serviceToUpdate, hairService);

        return repository.save(serviceToSave);
    }

    @Override
    public HairService cancel(Long id) {

        var hairService = findById(id);

        hairService.setActive(false);
        hairService.setCancellationDate(new Timestamp(System.currentTimeMillis()));

        return repository.save(hairService);
    }

    @Override
    public HairService activate(Long id) {

        var hairService = findById(id);

        hairService.setActive(true);
        hairService.setCancellationDate(null);

        return repository.save(hairService);
    }
}
