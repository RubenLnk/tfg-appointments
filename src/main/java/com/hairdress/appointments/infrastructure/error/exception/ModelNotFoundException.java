package com.hairdress.appointments.infrastructure.error.exception;

public class ModelNotFoundException extends RuntimeException {

  public ModelNotFoundException(String msg) {
    super(msg);
  }

  public ModelNotFoundException(String msg, Throwable cause) {
    super(msg, cause);
  }
}
