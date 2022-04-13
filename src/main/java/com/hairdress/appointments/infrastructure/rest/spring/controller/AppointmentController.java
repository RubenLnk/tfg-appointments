package com.hairdress.appointments.infrastructure.rest.spring.controller;

import com.hairdress.appointments.infrastructure.rest.spring.controller.mapper.AppointmentMapper;
import com.hairdress.appointments.infrastructure.rest.spring.controller.request.CreateAppointmentRequestDto;
import com.hairdress.appointments.infrastructure.rest.spring.controller.request.UpdateAppointmentRequestDto;
import com.hairdress.appointments.infrastructure.rest.spring.controller.response.AppointmentResponseDto;
import com.hairdress.appointments.infrastructure.rest.spring.controller.response.ErrorResponseDto;
import com.hairdress.appointments.infrastructure.service.AppointmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = {"Cita API"})
@RequestMapping("/appointment")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService service;
    private final AppointmentMapper mapper;

    @ApiOperation("Obtener todas las citas")
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK", response = AppointmentResponseDto.class),
        @ApiResponse(code = 400, message = "Datos proporcionados no válidos", response = ErrorResponseDto.class),
        @ApiResponse(code = 500, message = "Servicio no disponible", response = ErrorResponseDto.class)
    })
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AppointmentResponseDto>> findAll() {
        return ResponseEntity.ok().body(service.findAll().stream().map(mapper::toDto)
            .collect(Collectors.toList()));
    }

    @ApiOperation("Obtener cita por ID")
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK", response = AppointmentResponseDto.class),
        @ApiResponse(code = 400, message = "Datos proporcionados no válidos", response = ErrorResponseDto.class),
        @ApiResponse(code = 404, message = "Cita no encontrada", response = ErrorResponseDto.class),
        @ApiResponse(code = 500, message = "Servicio no disponible", response = ErrorResponseDto.class)
    })
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AppointmentResponseDto> findById(@ApiParam(name = "id",
        value = "ID de la cita", example = "1", required = true) @PathVariable("id") Long id) {

        return ResponseEntity.ok().body(mapper.toDto(service.findById(id)));
    }

    @ApiOperation("Obtener todas las citas de un día determinado")
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK", response = AppointmentResponseDto.class),
        @ApiResponse(code = 400, message = "Datos proporcionados no válidos", response = ErrorResponseDto.class),
        @ApiResponse(code = 500, message = "Servicio no disponible", response = ErrorResponseDto.class)
    })
    @GetMapping(value = "/by-day", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AppointmentResponseDto>> findById(@ApiParam(name = "day",
        value = "Día del que se desea obtener las citas", example = "2022-04-09T16:45:21.048",
        required = true)
        @DateTimeFormat(iso = ISO.DATE_TIME)
        @RequestParam("day") LocalDateTime day) {

        return ResponseEntity.ok().body(service.findAllAppointmentsInADay(day).stream()
            .map(mapper::toDto).collect(Collectors.toList()));
    }

    @ApiOperation("Crear cita")
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK", response = AppointmentResponseDto.class),
        @ApiResponse(code = 400, message = "Datos proporcionados no válidos", response = ErrorResponseDto.class),
        @ApiResponse(code = 500, message = "Servicio no disponible", response = ErrorResponseDto.class)
    })
    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AppointmentResponseDto> create(@ApiParam(name = "request",
        value = "Request para la creación de cita", required = true)
        @RequestBody @Valid CreateAppointmentRequestDto request) {

        return ResponseEntity.ok().body(mapper.toDto(service.save(
            mapper.createAppointmentRequestToEntity(request), request.getSevices())));
    }

    @ApiOperation("Borrar cita por ID")
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 400, message = "Datos proporcionados no válidos", response = ErrorResponseDto.class),
        @ApiResponse(code = 404, message = "Cita no encontrada", response = ErrorResponseDto.class),
        @ApiResponse(code = 500, message = "Servicio no disponible", response = ErrorResponseDto.class)
    })
    @DeleteMapping(value = "/{id}")
    public int delete(@ApiParam(name = "id",
        value = "ID de la cita", example = "1", required = true) @PathVariable("id") Long id) {

        service.delete(id);

        return HttpStatus.OK.value();
    }

    @ApiOperation("Actualiza una cita dado un ID")
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK", response = AppointmentResponseDto.class),
        @ApiResponse(code = 400, message = "Datos proporcionados no válidos", response = ErrorResponseDto.class),
        @ApiResponse(code = 404, message = "Cita no encontrada", response = ErrorResponseDto.class),
        @ApiResponse(code = 500, message = "Servicio no disponible", response = ErrorResponseDto.class)
    })
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AppointmentResponseDto> update(@ApiParam(name = "id",
        value = "ID de la cita", example = "1", required = true) @PathVariable("id") Long id,
        @ApiParam(name = "request", value = "Request para la actualización de cita",
            required = true) @RequestBody @Valid UpdateAppointmentRequestDto request) {

        return ResponseEntity.ok().body(mapper.toDto(service.update(
            id, mapper.updateAppointmentRequestToEntity(request), request.getServices())));
    }
}
