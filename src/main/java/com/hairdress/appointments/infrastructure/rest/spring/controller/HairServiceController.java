package com.hairdress.appointments.infrastructure.rest.spring.controller;

import com.hairdress.appointments.infrastructure.rest.spring.controller.mapper.HairServiceMapper;
import com.hairdress.appointments.infrastructure.rest.spring.controller.request.SaveHairServiceRequestDto;
import com.hairdress.appointments.infrastructure.rest.spring.controller.response.ErrorResponseDto;
import com.hairdress.appointments.infrastructure.rest.spring.controller.response.HairServiceResponseDto;
import com.hairdress.appointments.infrastructure.service.HairServiceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = {"Servicio API"})
@RequestMapping("/service")
@RequiredArgsConstructor
public class HairServiceController {

    private final HairServiceService service;
    private final HairServiceMapper mapper;

    @ApiOperation("Obtener todos los servicios")
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK", response = SaveHairServiceRequestDto.class),
        @ApiResponse(code = 400, message = "Datos proporcionados no válidos", response = ErrorResponseDto.class),
        @ApiResponse(code = 500, message = "Servicio no disponible", response = ErrorResponseDto.class)
    })
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<HairServiceResponseDto>> findAll() {
        return ResponseEntity.ok().body(service.findAll().stream().map(mapper::toDto)
            .collect(Collectors.toList()));
    }

    @ApiOperation("Obtener todos los servicios activos")
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK", response = SaveHairServiceRequestDto.class),
        @ApiResponse(code = 400, message = "Datos proporcionados no válidos", response = ErrorResponseDto.class),
        @ApiResponse(code = 500, message = "Servicio no disponible", response = ErrorResponseDto.class)
    })
    @GetMapping(value = "/actives", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<HairServiceResponseDto>> findAllActives() {
        return ResponseEntity.ok().body(service.findAllActives().stream().map(mapper::toDto)
            .collect(Collectors.toList()));
    }

    @ApiOperation("Obtener servicio por ID")
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK", response = SaveHairServiceRequestDto.class),
        @ApiResponse(code = 400, message = "Datos proporcionados no válidos", response = ErrorResponseDto.class),
        @ApiResponse(code = 404, message = "Servicio no encontrado", response = ErrorResponseDto.class),
        @ApiResponse(code = 500, message = "Servicio no disponible", response = ErrorResponseDto.class)
    })
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HairServiceResponseDto> findById(@ApiParam(name = "id",
        value = "ID del servicio", example = "1", required = true) @PathVariable("id") Long id) {

        return ResponseEntity.ok().body(mapper.toDto(service.findById(id)));
    }

    @ApiOperation("Crear un servicio")
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK", response = SaveHairServiceRequestDto.class),
        @ApiResponse(code = 400, message = "Datos proporcionados no válidos", response = ErrorResponseDto.class),
        @ApiResponse(code = 500, message = "Servicio no disponible", response = ErrorResponseDto.class)
    })
    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HairServiceResponseDto> create(
        @ApiParam(name = "hairService", value = "JSON con datos del servicio", required = true)
        @Valid @RequestBody SaveHairServiceRequestDto request) {

        return ResponseEntity.ok().body(mapper.toDto(
            service.save(mapper.createHairServiceRequestToEntity(request))));
    }

    @ApiOperation("Actualizar un servicio")
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK", response = SaveHairServiceRequestDto.class),
        @ApiResponse(code = 400, message = "Datos proporcionados no válidos", response = ErrorResponseDto.class),
        @ApiResponse(code = 404, message = "Servicio no encontrado", response = ErrorResponseDto.class),
        @ApiResponse(code = 500, message = "Servicio no disponible", response = ErrorResponseDto.class)
    })
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HairServiceResponseDto> update(
        @ApiParam(name = "id", value = "ID del servicio", example = "1", required = true)
        @PathVariable("id") Long id,
        @ApiParam(name = "hairService", value = "JSON con datos del servicio", required = true)
        @Valid @RequestBody SaveHairServiceRequestDto request) {

        return ResponseEntity.ok().body(mapper.toDto(
            service.update(id, mapper.updateHairServiceRequestToEntity(request))));
    }

    @ApiOperation("Desactiva un servicio")
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK", response = SaveHairServiceRequestDto.class),
        @ApiResponse(code = 400, message = "Datos proporcionados no válidos", response = ErrorResponseDto.class),
        @ApiResponse(code = 404, message = "Servicio no encontrado", response = ErrorResponseDto.class),
        @ApiResponse(code = 500, message = "Servicio no disponible", response = ErrorResponseDto.class)
    })
    @PatchMapping(value = "/cancel/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HairServiceResponseDto> cancel(
        @ApiParam(name = "id", value = "ID del servicio", example = "1", required = true)
        @PathVariable("id") Long id) {

        return ResponseEntity.ok().body(mapper.toDto(service.cancel(id)));
    }

    @ApiOperation("Activa un servicio")
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK", response = SaveHairServiceRequestDto.class),
        @ApiResponse(code = 400, message = "Datos proporcionados no válidos", response = ErrorResponseDto.class),
        @ApiResponse(code = 404, message = "Servicio no encontrado", response = ErrorResponseDto.class),
        @ApiResponse(code = 500, message = "Servicio no disponible", response = ErrorResponseDto.class)
    })
    @PatchMapping(value = "/activate/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HairServiceResponseDto> activate(
        @ApiParam(name = "id", value = "ID del servicio", example = "1", required = true)
        @PathVariable("id") Long id) {

        return ResponseEntity.ok().body(mapper.toDto(service.activate(id)));
    }

}
