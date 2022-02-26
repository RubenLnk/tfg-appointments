package com.hairdress.appointments.infrastructure.error.exception;

public class GenericException extends RuntimeException {

  public GenericException(String msg) {
    super(msg);
  }

  public GenericException(String msg, Throwable cause) {
    super(msg, cause);
  }
}
