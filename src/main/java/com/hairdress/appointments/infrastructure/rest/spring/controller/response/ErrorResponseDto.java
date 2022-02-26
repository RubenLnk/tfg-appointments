package com.hairdress.appointments.infrastructure.rest.spring.controller.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponseDto {

  @ApiModelProperty(value = "Código de error", example = "XXX")
  private String code;

  @ApiModelProperty(value = "Descripción del error", example = "Resource Not Found")
  private String description;
}
