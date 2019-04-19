package com.example.model;

public class AppException extends RuntimeException {

  public final ErrorType errorType;

  public AppException(final ErrorType errorType, final String message) {
    super(message);
    this.errorType = errorType;
  }
}
