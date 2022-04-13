package com.hairdress.appointments.infrastructure.rest.spring.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SaveHairServiceRequestDto {

    @ApiModelProperty(value = "Nombre del servicio",
        example = "Corte de barba")
    @NotNull(message = "El nombre del servicio es obligatorio")
    @JsonProperty("name")
    private String name;

    @ApiModelProperty(value = "Descripción del servicio",
        example = "Corte de barba para caballero, apurado con cuchilla y "
            + "baño de toalla fría y caliente")
    @NotNull(message = "La descripción del servicio es obligatoria")
    @JsonProperty("description")
    private String description;

    @ApiModelProperty(value = "Precio del servicio",
        example = "7.5")
    @NotNull(message = "El precio del servicio es obligatorio")
    @JsonProperty("price")
    private String price;

    @ApiModelProperty(value = "Duración (en minutos) del servicio",
        example = "30")
    @NotNull(message = "La duración del servicio es obligatoria")
    @JsonProperty("duration")
    private String duration;
}
