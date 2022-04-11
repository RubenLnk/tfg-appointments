package com.hairdress.appointments.infrastructure.service.impl;

import com.hairdress.appointments.infrastructure.bbdd.models.HairService;
import com.hairdress.appointments.infrastructure.bbdd.repositories.HairServiceRepository;
import com.hairdress.appointments.infrastructure.error.exception.ModelNotFoundException;
import com.hairdress.appointments.infrastructure.service.HairServiceService;
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

    @Override
    public HairService findById(Long id) {

        Optional<HairService> opt = repository.findById(id);

        if (opt.isEmpty()) {
            log.error(SERVICE_ID_NOT_FOUND, id);
            throw new ModelNotFoundException(SERVICE_ID_NOT_FOUND_EXCEPTION_TEXT + id);
        }

        return opt.get();
    }
}
