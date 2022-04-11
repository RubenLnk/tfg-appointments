package com.hairdress.appointments.infrastructure.rest.spring.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfessionalResponseDto {

  @ApiModelProperty(value = "ID del profesional", example = "57")
  @JsonProperty("id")
  private Long id;

  @ApiModelProperty(value = "UID del profesional", example = "rjr17")
  @JsonProperty("uid")
  private String uid;

  @ApiModelProperty(value = "Nombre del profesional", example = "Juan")
  @JsonProperty("name")
  private String name;

  @ApiModelProperty(value = "Primer apellido del profesional", example = "Fernandez")
  @JsonProperty("surname1")
  private String surname1;

  @ApiModelProperty(value = "Segundo apellido del profesional", example = "Lozano")
  @JsonProperty("surname2")
  private String surname2;
}
