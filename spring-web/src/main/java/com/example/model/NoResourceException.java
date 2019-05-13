package com.example.model;

public class NoResourceException extends RuntimeException {

  public NoResourceException(final String message) {
    super(message == null ? "no resource error" : message);
  }
}
