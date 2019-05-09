package com.example.model;

public class NoResourceException extends RuntimeException {

  public NoResourceException(final String message) {
    super(message);
  }
}
