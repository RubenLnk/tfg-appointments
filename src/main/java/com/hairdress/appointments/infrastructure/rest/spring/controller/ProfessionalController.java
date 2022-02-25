package com.hairdress.appointments.infrastructure.rest.spring.controller;

import com.hairdress.appointments.infrastructure.bbdd.models.Professional;
import com.hairdress.appointments.infrastructure.rest.spring.controller.mapper.ProfessionalMapper;
import com.hairdress.appointments.infrastructure.rest.spring.controller.response.ErrorResponseDto;
import com.hairdress.appointments.infrastructure.rest.spring.controller.response.ProfessionalResponseDto;
import com.hairdress.appointments.infrastructure.service.ProfessionalService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = {"Professional API"})
@RequestMapping("/professional")
@RequiredArgsConstructor
public class ProfessionalController {

  private final ProfessionalService service;
  private final ProfessionalMapper mapper;

  @ApiOperation("Get professional by his ID")
  @ApiResponses({
      @ApiResponse(code = 200, message = "OK", response = ProfessionalResponseDto.class),
      @ApiResponse(code = 400, message = "Bad request error", response = ErrorResponseDto.class),
      @ApiResponse(code = 404, message = "Professional not found", response = ErrorResponseDto.class),
      @ApiResponse(code = 503, message = "Server not available", response = ErrorResponseDto.class)
  })
  @GetMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ProfessionalResponseDto> getById(
      @ApiParam(name = "id", value = "Professional identifier", example = "12", required = true)
      @PathVariable("id") Long id) {
    return ResponseEntity.ok().body(mapper.toDto(service.findById(id)));
  }

  @ApiOperation("Get all professionals")
  @ApiResponses({
      @ApiResponse(code = 200, message = "OK", response = ProfessionalResponseDto.class),
      @ApiResponse(code = 400, message = "Bad request error", response = ErrorResponseDto.class),
      @ApiResponse(code = 404, message = "Professional not found", response = ErrorResponseDto.class),
      @ApiResponse(code = 503, message = "Server not available", response = ErrorResponseDto.class)
  })
  @GetMapping(value="/", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<ProfessionalResponseDto>> getById() {
    return ResponseEntity.ok().body(service.findAll().stream().map(mapper::toDto)
        .collect(Collectors.toList()));
  }

}
