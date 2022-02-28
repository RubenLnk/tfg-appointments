package com.hairdress.appointments.infrastructure.rest.spring.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateProfessionalRequestDto {

  @ApiModelProperty(value = "Nombre del profesional", example = "Ruben")
  @NotNull(message = "El nombre del profesional es obligatorio")
  @JsonProperty("name")
  private String name;

  @ApiModelProperty(value = "Primer apellido del profesional", example = "Jimenez")
  @NotNull(message = "El primer apellido del profesional es obligatorio")
  @JsonProperty("surname1")
  private String surname1;

  @ApiModelProperty(value = "Segundo apellido del profesional", example = "Romero")
  @JsonProperty("surname2")
  private String surname2;

}
