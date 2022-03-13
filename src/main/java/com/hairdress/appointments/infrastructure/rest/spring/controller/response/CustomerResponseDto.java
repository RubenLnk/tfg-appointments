package com.hairdress.appointments.infrastructure.rest.spring.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponseDto {

  @ApiModelProperty(value = "ID del cliente", example = "57")
  @JsonProperty("id")
  private Long id;

  @ApiModelProperty(value = "Nombre del cliente", example = "Fernando")
  @JsonProperty("name")
  private String name;

  @ApiModelProperty(value = "Primer apellido del cliente", example = "Hernandez")
  @JsonProperty("surname1")
  private String surname1;

  @ApiModelProperty(value = "Segundo apellido del cliente", example = "Sanchez")
  @JsonProperty("surname2")
  private String surname2;

  @ApiModelProperty(value = "Teléfono del cliente", example = "612345678")
  @JsonProperty("phone")
  private String phone;

  @ApiModelProperty(value = "Correo electrónico del cliente", example = "fernando@info.com")
  @JsonProperty("email")
  private String email;

  @ApiModelProperty(value = "Indica si el cliente está registrado en la aplicación",
      example = "true")
  @JsonProperty("registered")
  private String registered;

}
