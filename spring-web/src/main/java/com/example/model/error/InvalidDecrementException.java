package com.example.model.error;

public class InvalidDecrementException extends RuntimeException {

  public InvalidDecrementException() {
    super("invalid decrement");
  }

  public InvalidDecrementException(final String message) {
    super(message);
  }
}
