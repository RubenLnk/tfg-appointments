package com.hairdress.appointments.infrastructure.rest.spring.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HairServiceResponseDto {

    @ApiModelProperty(value = "ID del servicio", example = "57")
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty(value = "Nombre del servicio", example = "Corte de barba")
    @JsonProperty("name")
    private String name;

    @ApiModelProperty(value = "Precio del servicio", example = "15.50")
    @JsonProperty("price")
    private Double price;

    @ApiModelProperty(value = "Duración (en minutos) del servicio", example = "30")
    @JsonProperty("duration")
    private Integer duration;
}
