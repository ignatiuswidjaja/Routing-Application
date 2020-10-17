package com.example.routing.model.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class StationNotFoundException extends RuntimeException {
  public StationNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }
}
