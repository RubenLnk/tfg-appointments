package com.hairdress.appointments.infrastructure.error;

import com.hairdress.appointments.infrastructure.error.exception.AuthorizationException;
import com.hairdress.appointments.infrastructure.error.exception.BadRequestException;
import com.hairdress.appointments.infrastructure.error.exception.GenericException;
import com.hairdress.appointments.infrastructure.error.exception.ModelNotFoundException;
import com.hairdress.appointments.infrastructure.error.exception.SecurePasswordException;
import com.hairdress.appointments.infrastructure.error.exception.UserAlreadyExistsException;
import com.hairdress.appointments.infrastructure.rest.spring.controller.response.ErrorResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

  private static final String ERROR_MESSAGE = "errorMessage";

  @ExceptionHandler({AuthorizationException.class})
  public ResponseEntity<ErrorResponseDto> errorUnauthorizedExceptionHandler(Exception e) {
    var msg = e.getMessage();
    log.debug("Exception Handler - UnauthorizedException - {}", msg);

    return ResponseEntity
        .status(HttpStatus.UNAUTHORIZED)
        .header(ERROR_MESSAGE, msg)
        .body(new ErrorResponseDto(String.valueOf(HttpStatus.UNAUTHORIZED.value()), msg));
  }

  @ExceptionHandler({UserAlreadyExistsException.class, BadRequestException.class})
  public ResponseEntity<ErrorResponseDto> errorBadRequestExceptionHandler(Exception e) {
    var msg = e.getMessage();
    log.debug("Exception Handler - BadRequestException - {}", msg);

    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .header(ERROR_MESSAGE, msg)
        .body(new ErrorResponseDto(String.valueOf(HttpStatus.BAD_REQUEST.value()), msg));
  }

  @ExceptionHandler({ModelNotFoundException.class})
  public ResponseEntity<ErrorResponseDto> errorNotFoundExceptionHandler(Exception e) {
    var msg = e.getMessage();
    log.debug("Exception Handler - NotFoundException - {}", msg);

    return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .header(ERROR_MESSAGE, msg)
        .body(new ErrorResponseDto(String.valueOf(HttpStatus.NOT_FOUND.value()), msg));
  }

  @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
  public ResponseEntity<ErrorResponseDto> errorValidationExceptionHandler(Exception e) {
    var msg = e.getMessage();
    log.debug("Exception Handler - ValidationException - {}", msg);

    BindingResult br = null;

    if (e instanceof MethodArgumentNotValidException) {
      br = ((MethodArgumentNotValidException) e).getBindingResult();
    } else {
      br = ((BindException) e).getBindingResult();
    }

    BindingResult finalBr = br;

    try {
      var wrongAttribute = ((FieldError) finalBr.getAllErrors().get(0)).getField();
      var errorMessage = finalBr.getAllErrors().get(0).getDefaultMessage();
      msg = wrongAttribute + " - " + errorMessage;
    } catch (Exception e2) {
      msg = e.getMessage();
    }

    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .header(ERROR_MESSAGE, msg)
        .body(new ErrorResponseDto(String.valueOf(HttpStatus.BAD_REQUEST.value()), msg));
  }

  @ExceptionHandler({SecurePasswordException.class, GenericException.class, Exception.class})
  public ResponseEntity<ErrorResponseDto> errorNotControlledExceptions(Exception e) {
    var msg = e.getMessage();
    log.error("Exception Handler - Generic Exceptions - {}", msg, e);

    return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .header(ERROR_MESSAGE, msg)
        .body(new ErrorResponseDto(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), msg));
  }

}
