package com.hairdress.appointments.infrastructure.error.exception;

public class UserAlreadyExistsException extends RuntimeException {

  public UserAlreadyExistsException(String msg) {
    super(msg);
  }

  public UserAlreadyExistsException(String msg, Throwable cause) {
    super(msg, cause);
  }
}
