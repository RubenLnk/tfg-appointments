package com.hairdress.appointments.infrastructure.error.exception;

public class SecurePasswordException extends RuntimeException {

  public SecurePasswordException(String msg) {
    super(msg);
  }

  public SecurePasswordException(String msg, Throwable cause) {
    super(msg, cause);
  }
}
