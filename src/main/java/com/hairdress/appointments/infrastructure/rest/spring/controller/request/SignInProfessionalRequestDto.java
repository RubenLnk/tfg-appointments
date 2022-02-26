package com.hairdress.appointments.infrastructure.rest.spring.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SignInProfessionalRequestDto {

  @ApiModelProperty(value = "UID del profesional", example = "rjr17")
  @NotNull(message = "El UID del profesional es obligatorio")
  @JsonProperty("uid")
  private String uid;

  @ApiModelProperty(value = "Contraseña del profesional", example = "Secr3t_74?")
  @NotNull(message = "La contraseña del profesional es obligatoria")
  @JsonProperty("password")
  private String password;
}
