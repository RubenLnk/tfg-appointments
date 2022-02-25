package com.hairdress.appointments.infrastructure.rest.spring.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfessionalResponseDto {

  @ApiModelProperty(value = "Professional ID", example = "57")
  @JsonProperty("id")
  private Long id;

  @ApiModelProperty(value = "Professional user id", example = "rjr17")
  @JsonProperty("uid")
  private String uid;

  @ApiModelProperty(value = "Professional name", example = "Juan")
  @JsonProperty("name")
  private String nombre;

  @ApiModelProperty(value = "Professional first surname", example = "Fernandez")
  @JsonProperty("surname1")
  private String apellido1;

  @ApiModelProperty(value = "Professional second surname", example = "Lozano")
  @JsonProperty("surname2")
  private String apellido2;
}
