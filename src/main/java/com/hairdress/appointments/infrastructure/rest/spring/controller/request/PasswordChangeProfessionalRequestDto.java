package com.hairdress.appointments.infrastructure.rest.spring.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PasswordChangeProfessionalRequestDto {

  @ApiModelProperty(value = "UID del profesional", example = "rjr17")
  @NotNull(message = "El UID del profesional es obligatorio")
  @JsonProperty("uid")
  private String uid;

  @ApiModelProperty(value = "Contraseña antigua del profesional", example = "Secr3t_74?")
  @NotNull(message = "La contraseña antigua es obligatoria")
  @JsonProperty("old_password")
  private String oldPassword;

  @ApiModelProperty(value = "Contraseña nueva del profesional", example = "PassW0rd_26!")
  @NotNull(message = "La contraseña nueva es obligatoria")
  @JsonProperty("new_password")
  private String newPassword;
}
