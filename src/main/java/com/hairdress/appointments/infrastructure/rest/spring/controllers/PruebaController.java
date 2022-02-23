package com.hairdress.appointments.infrastructure.rest.spring.controllers;

import com.hairdress.appointments.infrastructure.bbdd.models.Professional;
import com.hairdress.appointments.infrastructure.bbdd.repositories.ProfessionalRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/prueba")
@RequiredArgsConstructor
public class PruebaController {

    private final ProfessionalRepository professionalRepository;

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Professional>> prueba() {

        return ResponseEntity.ok().body(professionalRepository.findAll());
    }

    @Data
    static class PruebaClass {
        private String var1;
        private int var2;
        private boolean var3;
    }
}
