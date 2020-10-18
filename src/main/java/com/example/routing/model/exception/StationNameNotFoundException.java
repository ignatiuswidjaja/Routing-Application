package com.example.routing.model.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class StationNameNotFoundException extends BaseNotFoundException {
  private final String stationName;

  @Override
  public String getMessage() {
    return String.format("Station %s is not found", stationName);
  }
}
