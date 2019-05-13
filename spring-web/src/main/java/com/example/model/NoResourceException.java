package com.example.model;

public class NoResourceException extends RuntimeException {

  public NoResourceException() {
    super("no resource found");
  }

  public NoResourceException(final String message) {
    super(message);
  }
}
