package com.example.controller.error;

import com.example.model.ErrorType;
import org.springframework.http.HttpStatus;

public enum ErrorTypeMapper {
  USER_INPUT(HttpStatus.BAD_REQUEST),
  AUTHORIZATION(HttpStatus.UNAUTHORIZED),
  AUTHENTICATION(HttpStatus.FORBIDDEN),
  NO_RESOURCE(HttpStatus.NOT_FOUND),
  CONDITION(HttpStatus.CONFLICT),
  ERROR(HttpStatus.INTERNAL_SERVER_ERROR),
  ;

  public final HttpStatus status;

  ErrorTypeMapper(final HttpStatus status) {
    this.status = status;
  }

  public static ErrorTypeMapper of(ErrorType type) {
    switch (type) {
      case USER_INPUT:
        return USER_INPUT;
      case CONDITION:
        return CONDITION;
      case NO_RESOURCE:
        return NO_RESOURCE;
      case ERROR:
        return ERROR;
      case AUTHORIZATION:
        return AUTHORIZATION;
      case AUTHENTICATION:
        return AUTHENTICATION;
      default:
        throw new IllegalArgumentException("no value for " + type);
    }
  }
}
