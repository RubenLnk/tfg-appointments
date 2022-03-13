package com.hairdress.appointments.infrastructure.rest.spring.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SignUpCustomerRequestDto {

  @ApiModelProperty(value = "Nombre del cliente", example = "Antonio")
  @NotNull(message = "El nombre del cliente es obligatorio")
  @JsonProperty("name")
  private String name;

  @ApiModelProperty(value = "Primer apellido del cliente", example = "Jimenez")
  @NotNull(message = "El primer apellido del cliente es obligatorio")
  @JsonProperty("surname1")
  private String surname1;

  @ApiModelProperty(value = "Segundo apellido del cliente", example = "Rodriguez")
  @JsonProperty("surname2")
  private String surname2;

  @ApiModelProperty(value = "Teléfono del cliente", example = "+34612345678")
  @NotNull(message = "El teléfono del cliente es obligatorio")
  @JsonProperty("phone")
  private String phone;

  @ApiModelProperty(value = "Correo electrónico del cliente", example = "antonio@example.com")
  @JsonProperty("email")
  private String email;
}
