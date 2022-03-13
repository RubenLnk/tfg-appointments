package com.hairdress.appointments.infrastructure.rest.spring.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SignInCustomerRequestDto {

  @ApiModelProperty(value = "Correo electrónico del cliente", example = "antonio@example.com")
  @NotNull(message = "El correo del cliente es obligatorio")
  @JsonProperty("email")
  private String email;

  @ApiModelProperty(value = "Contraseña del cliente", example = "Secr3t_74?")
  @NotNull(message = "La contraseña del cliente es obligatoria")
  @JsonProperty("password")
  private String password;
}
