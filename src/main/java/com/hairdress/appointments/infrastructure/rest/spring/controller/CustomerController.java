package com.hairdress.appointments.infrastructure.rest.spring.controller;

import com.hairdress.appointments.infrastructure.rest.spring.controller.mapper.CustomerMapper;
import com.hairdress.appointments.infrastructure.rest.spring.controller.request.PasswordChangeCustomerRequestDto;
import com.hairdress.appointments.infrastructure.rest.spring.controller.request.SignInCustomerRequestDto;
import com.hairdress.appointments.infrastructure.rest.spring.controller.request.SignUpCustomerRequestDto;
import com.hairdress.appointments.infrastructure.rest.spring.controller.request.UpdateCustomerRequestDto;
import com.hairdress.appointments.infrastructure.rest.spring.controller.response.CustomerResponseDto;
import com.hairdress.appointments.infrastructure.rest.spring.controller.response.ErrorResponseDto;
import com.hairdress.appointments.infrastructure.service.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = {"Cliente API"})
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService service;
    private final CustomerMapper mapper;

    @ApiOperation("Obtener todos los clientes")
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK", response = CustomerResponseDto.class),
        @ApiResponse(code = 400, message = "Datos proporcionados no válidos", response = ErrorResponseDto.class),
        @ApiResponse(code = 404, message = "Cliente no encontrado", response = ErrorResponseDto.class),
        @ApiResponse(code = 500, message = "Servicio no disponible", response = ErrorResponseDto.class)
    })
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CustomerResponseDto>> getAll() {
        return ResponseEntity.ok().body(service.findAll().stream().map(mapper::toDto)
            .collect(Collectors.toList()));
    }

    @ApiOperation("Obtener cliente por teléfono")
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK", response = CustomerResponseDto.class),
        @ApiResponse(code = 400, message = "Datos proporcionados no válidos", response = ErrorResponseDto.class),
        @ApiResponse(code = 404, message = "Cliente no encontrado", response = ErrorResponseDto.class),
        @ApiResponse(code = 500, message = "Servicio no disponible", response = ErrorResponseDto.class)
    })
    @GetMapping(value = "/by-phone", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerResponseDto> getByPhone(
        @ApiParam(name = "phone", value = "Teléfono del cliente", example = "+34612345678", required = true)
        @RequestParam("phone") String phone) {
        return ResponseEntity.ok().body(mapper.toDto(service.findByPhone(phone)));
    }

    @ApiOperation("Crear cliente nuevo")
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK", response = CustomerResponseDto.class),
        @ApiResponse(code = 400, message = "Datos proporcionados no válidos", response = ErrorResponseDto.class),
        @ApiResponse(code = 404, message = "Cliente no encontrado", response = ErrorResponseDto.class),
        @ApiResponse(code = 500, message = "Servicio no disponible", response = ErrorResponseDto.class)
    })
    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerResponseDto> create(
        @ApiParam(name = "customer", value = "JSON con datos del cliente", required = true)
        @Valid @RequestBody SignUpCustomerRequestDto customer) {
        return ResponseEntity.ok().body(mapper.toDto(
            service.signUp(mapper.signUpCustomerToEntity(customer))));
    }

    @ApiOperation("Asigna un correo electrónico a un cliente")
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK", response = CustomerResponseDto.class),
        @ApiResponse(code = 400, message = "Datos proporcionados no válidos", response = ErrorResponseDto.class),
        @ApiResponse(code = 404, message = "Cliente no encontrado", response = ErrorResponseDto.class),
        @ApiResponse(code = 500, message = "Servicio no disponible", response = ErrorResponseDto.class)
    })
    @PatchMapping(value = "/register-email", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerResponseDto> registerEmail(
        @ApiParam(name = "phone", value = "Teléfono del cliente", example = "+34612345678",
            required = true) @RequestParam String phone,
        @ApiParam(name = "email", value = "Correo del cliente que vamos a asignar",
            example = "antonio@example.com", required = true) @RequestParam String email) {
        return ResponseEntity.ok().body(mapper.toDto(
            service.registerEmail(phone, email)));
    }

    @ApiOperation("Actualiza los datos de un cliente")
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK", response = CustomerResponseDto.class),
        @ApiResponse(code = 400, message = "Datos proporcionados no válidos", response = ErrorResponseDto.class),
        @ApiResponse(code = 404, message = "Cliente no encontrado", response = ErrorResponseDto.class),
        @ApiResponse(code = 500, message = "Servicio no disponible", response = ErrorResponseDto.class)
    })
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerResponseDto> updateCustomer(
        @ApiParam(name = "id", value = "ID del cliente", example = "1",
            required = true) @PathVariable("id") Long id,
        @ApiParam(name = "customer", value = "JSON con datos actualizados del cliente", required = true)
        @Valid @RequestBody UpdateCustomerRequestDto request) {
        return ResponseEntity.ok().body(mapper.toDto(
            service.update(id, mapper.updateCustomerToEntity(request))));
    }

    @ApiOperation("Borra un cliente")
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 400, message = "Datos proporcionados no válidos", response = ErrorResponseDto.class),
        @ApiResponse(code = 404, message = "Cliente no encontrado", response = ErrorResponseDto.class),
        @ApiResponse(code = 500, message = "Servicio no disponible", response = ErrorResponseDto.class)
    })
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public int deleteCustomer(
        @ApiParam(name = "id", value = "ID del cliente", example = "1", required = true)
        @PathVariable("id") Long id) {

        service.delete(id);

        return HttpStatus.OK.value();
    }

    @ApiOperation("Cambio de contraseña del cliente")
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 400, message = "Datos proporcionados no válidos", response = ErrorResponseDto.class),
        @ApiResponse(code = 404, message = "Cliente no encontrado", response = ErrorResponseDto.class),
        @ApiResponse(code = 500, message = "Servicio no disponible", response = ErrorResponseDto.class)
    })
    @PatchMapping(value="/change-password", consumes = MediaType.APPLICATION_JSON_VALUE)
    public int changePassword(@ApiParam(name = "professional", value = "JSON con los datos de cambio"
        + " de contraseña del cliente", required = true)
    @Valid @RequestBody PasswordChangeCustomerRequestDto request) {

        service.changePassword(request.getEmail(), request.getOldPassword(),
            request.getNewPassword());

        return HttpStatus.OK.value();
    }

    @ApiOperation("Login del cliente")
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 400, message = "Datos proporcionados no válidos", response = ErrorResponseDto.class),
        @ApiResponse(code = 404, message = "Cliente no encontrado", response = ErrorResponseDto.class),
        @ApiResponse(code = 500, message = "Servicio no disponible", response = ErrorResponseDto.class)
    })
    @PostMapping(value="/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public int login(@ApiParam(name = "customer", value = "JSON con los datos de ingreso"
        + " del cliente", required = true)
    @Valid @RequestBody SignInCustomerRequestDto customer) {

        service.login(customer.getEmail(), customer.getPassword());

        return HttpStatus.OK.value();
    }

    @ApiOperation("Primera conexión del cliente")
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 400, message = "Datos proporcionados no válidos", response = ErrorResponseDto.class),
        @ApiResponse(code = 404, message = "Cliente no encontrado", response = ErrorResponseDto.class),
        @ApiResponse(code = 500, message = "Servicio no disponible", response = ErrorResponseDto.class)
    })
    @PostMapping(value="/first-conn", consumes = MediaType.APPLICATION_JSON_VALUE)
    public int firstConnection(@ApiParam(name = "customer", value = "JSON con los datos de ingreso"
        + " del cliente", required = true)
        @Valid @RequestBody SignInCustomerRequestDto customer) {

        service.firstConnection(customer.getEmail(), customer.getPassword());

        return HttpStatus.OK.value();
    }

    @ApiOperation("Comprueba si es la primera conexión del cliente")
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 400, message = "Datos proporcionados no válidos", response = ErrorResponseDto.class),
        @ApiResponse(code = 404, message = "Cliente no encontrado", response = ErrorResponseDto.class),
        @ApiResponse(code = 500, message = "Servicio no disponible", response = ErrorResponseDto.class)
    })
    @GetMapping(value="/is-first-conn", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> isFirstConnection(@ApiParam(name = "email",
        value = "Correo electrónico del cliente", example = "antonio@example.com", required = true)
        @Valid @RequestParam("email") String email) {

        boolean isFirstConnection = service.isFirstConnection(email);

        return ResponseEntity.ok().body(isFirstConnection);
    }

}
