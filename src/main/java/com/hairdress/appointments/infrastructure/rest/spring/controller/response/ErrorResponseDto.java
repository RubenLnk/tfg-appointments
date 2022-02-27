package com.hairdress.appointments.infrastructure.rest.spring.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponseDto {

  @ApiModelProperty(value = "Código de error", example = "XXX")
  @JsonProperty("codigo")
  private String code;

  @ApiModelProperty(value = "Descripción del error", example = "Resource Not Found")
  @JsonProperty("descripcion")
  private String description;
}
