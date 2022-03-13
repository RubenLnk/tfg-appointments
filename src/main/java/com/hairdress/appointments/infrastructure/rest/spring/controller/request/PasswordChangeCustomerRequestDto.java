package com.hairdress.appointments.infrastructure.rest.spring.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PasswordChangeCustomerRequestDto {

    @ApiModelProperty(value = "Correo electrónico del cliente", example = "antonio@example.com")
    @NotNull(message = "El correo del cliente es obligatorio")
    @JsonProperty("email")
    private String email;

    @ApiModelProperty(value = "Contraseña antigua del cliente", example = "Secr3t_74?")
    @NotNull(message = "La contraseña antigua es obligatoria")
    @JsonProperty("old_password")
    private String oldPassword;

    @ApiModelProperty(value = "Contraseña nueva del cliente", example = "PassW0rd_26!")
    @NotNull(message = "La contraseña nueva es obligatoria")
    @JsonProperty("new_password")
    private String newPassword;
}
