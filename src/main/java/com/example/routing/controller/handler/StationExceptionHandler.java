package com.example.routing.controller.handler;

import com.example.routing.model.exception.StationNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class StationExceptionHandler extends ResponseEntityExceptionHandler {
  @ExceptionHandler({StationNotFoundException.class})
  protected ResponseEntity<Object> handleNotFound(Exception exception, WebRequest request) {
    return handleExceptionInternal(
        exception,
        "Station not found",
        new HttpHeaders(),
        HttpStatus.NOT_FOUND,
        request
    );
  }
}
