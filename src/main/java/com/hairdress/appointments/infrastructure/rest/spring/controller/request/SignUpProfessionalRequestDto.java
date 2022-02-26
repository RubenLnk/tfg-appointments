package com.hairdress.appointments.infrastructure.rest.spring.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SignUpProfessionalRequestDto {

  @ApiModelProperty(value = "UID del profesional", example = "rjr17")
  @NotNull(message = "El UID del profesional es obligatorio")
  @JsonProperty("uid")
  private String uid;

  @ApiModelProperty(value = "Contraseña del profesional", example = "Secr3t_74?")
  @NotNull(message = "La contraseña del profesional es obligatoria")
  @JsonProperty("password")
  private String password;

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
