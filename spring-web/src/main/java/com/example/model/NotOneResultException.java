package com.example.model;

public class NotOneResultException extends RuntimeException {

  public NotOneResultException() {
    super("entity result is not one");
  }

  public NotOneResultException(final String message) {
    super(message);
  }
}
