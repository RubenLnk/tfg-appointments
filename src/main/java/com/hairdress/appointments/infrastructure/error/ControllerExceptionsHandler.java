package com.hairdress.appointments.infrastructure.error;

import com.hairdress.appointments.infrastructure.error.exception.ModelNotFoundException;
import com.hairdress.appointments.infrastructure.rest.spring.controller.response.ErrorResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ControllerExceptionsHandler {

  private static final String ERROR_MESSAGE = "errorMessage";

  @ExceptionHandler({ModelNotFoundException.class})
  public ResponseEntity<ErrorResponseDto> errorNotFoundExceptionHandler(Exception e) {
    var msg = e.getMessage();
    log.debug("Exception Handler - NotFoundException - {}", msg);

    return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .header(ERROR_MESSAGE, msg)
        .body(new ErrorResponseDto(String.valueOf(HttpStatus.NOT_FOUND.value()), msg));
  }

}