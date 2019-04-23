package com.example.controller.error;

import com.example.json.AppJson;
import com.example.model.AppException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

  private static final Logger logger = LoggerFactory.getLogger(ResponseEntityExceptionHandler.class);

  @ExceptionHandler({AppException.class})
  ResponseEntity<Object> handle(Exception th, final WebRequest webRequest) {
    final Map<String, String[]> map = webRequest.getParameterMap();
    final Map<String, List<String>> param = map.entrySet()
        .stream()
        .collect(Collectors.toUnmodifiableMap(Entry::getKey,
            entry -> Arrays.stream(entry.getValue()).collect(Collectors.toUnmodifiableList())));
    logger.info("error at {}, {}", webRequest.getContextPath(), param);
    final AppJson body = AppJson.failure(th.getMessage());
    HttpStatus status =
        (th instanceof AppException) ?
            ErrorTypeMapper.of(((AppException) th).errorType).status :
            HttpStatus.INTERNAL_SERVER_ERROR;
    return handleExceptionInternal(
        th, body, new HttpHeaders(), status, webRequest);
  }

  @ExceptionHandler({ IllegalArgumentException.class })
  ResponseEntity<Object> handleErrorArgument(RuntimeException e, WebRequest webRequest) {
    final Map<String, String[]> map = webRequest.getParameterMap();
    final Map<String, List<String>> param = map.entrySet()
        .stream()
        .collect(Collectors.toUnmodifiableMap(Entry::getKey,
            entry -> Arrays.stream(entry.getValue()).collect(Collectors.toUnmodifiableList())));
    logger.info("error at {}, {}", webRequest.getContextPath(), param);
    final String message = e.getMessage();
    final String msg = message == null ? "invalid argument" : message;
    final AppJson json = AppJson.failure(msg);
    return handleExceptionInternal(e, json, new HttpHeaders(), HttpStatus.BAD_REQUEST, webRequest);
  }
}
