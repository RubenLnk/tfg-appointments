package com.hairdress.appointments.infrastructure.rest.spring.controller.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
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

    @ApiModelProperty(value = "Indicador de si el servicio sigue activo o se ha anulado",
        example = "true")
    @JsonProperty("active")
    private Boolean active;

    @ApiModelProperty(value = "Fecha de cancelación (si el servicio ha sido cancelado)",
        example = "2022-04-09T14:45:21.048+00:00")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS", timezone = "Europe/Madrid")
    @JsonProperty("cancellationDate")
    private Timestamp cancellationDate;
}
