package com.hairdress.appointments.infrastructure.rest.spring.controller.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import java.sql.Timestamp;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateAppointmentRequestDto {

    @ApiModelProperty(value = "Fecha de inicio de la cita",
        example = "2022-04-09T14:45:21.048")
    @NotNull(message = "La fecha de inicio de la cita es obligatoria")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS", timezone = "Europe/Madrid")
    @JsonProperty("appointmentInitDate")
    private Timestamp appointmentInitDate;

    @ApiModelProperty(value = "Fecha de fin de la cita",
        example = "2022-04-09T16:45:21.048")
    @NotNull(message = "La fecha de fin de la cita es obligatoria")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS", timezone = "Europe/Madrid")
    @JsonProperty("appointmentEndDate")
    private Timestamp appointmentEndDate;

    @ApiModelProperty(value = "Precio total de la cita", example = "15.50")
    @NotNull(message = "El precio de la cita es obligatorio")
    @JsonProperty("price")
    private Double price;

    @ApiModelProperty(value = "Observaciones para la cita",
        example = "La cita es para mi hijo de 4 años")
    @JsonProperty("observations")
    private String observations;

    @ApiModelProperty(value = "Lista de IDs de los servicios asociados a la cita")
    @NotNull(message = "La lista de servicios no puede ser nula")
    @NotEmpty(message = "Debe seleccionar, al menos, un servicio")
    @JsonProperty("services")
    private List<Long> services;
}
