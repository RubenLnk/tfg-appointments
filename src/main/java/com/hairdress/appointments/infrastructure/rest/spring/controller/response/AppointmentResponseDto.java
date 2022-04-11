package com.hairdress.appointments.infrastructure.rest.spring.controller.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import java.sql.Timestamp;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class AppointmentResponseDto {

    @ApiModelProperty(value = "ID de la cita", example = "57")
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty(value = "Fecha de inicio de la cita",
        example = "2022-04-09T14:45:21.048+00:00")
    @JsonProperty("appointmentInitDate")
    private Timestamp appointmentInitDate;

    @ApiModelProperty(value = "Fecha de fin de la cita",
        example = "2022-04-09T16:45:21.048+00:00")
    @JsonProperty("appointmentEndDate")
    private Timestamp appointmentEndDate;

    @ApiModelProperty(value = "Precio total de la cita", example = "15.50")
    @JsonProperty("price")
    private Double price;

    @ApiModelProperty(value = "Observaciones para la cita",
        example = "La cita es para mi hijo de 4 años")
    @JsonProperty("observations")
    private String observations;

    @ApiModelProperty(value = "Datos del cliente de la cita")
    @JsonProperty("customer")
    private CustomerResponseDto customer;

    @ApiModelProperty(value = "Profesional que ha creado la cita")
    @JsonProperty("creatorProfessional")
    private ProfessionalResponseDto creatorProfessional;

    @ApiModelProperty(value = "Indicador de si la cita sigue activa o se ha anulado", example = "true")
    @JsonProperty("active")
    private Boolean active;

    @ApiModelProperty(value = "Fecha de cancelación (si la cita ha sido cancelada)",
        example = "2022-04-09T14:45:21.048+00:00")
    @JsonProperty("cancellationDate")
    private Timestamp cancellationDate;

    @ApiModelProperty(value = "Lista de servicios de la cita")
    @JsonProperty("services")
    private List<HairServiceResponseDto> services;
}
