package com.example.routing.model.exception;

public class StationNameNotFoundException extends RuntimeException {
  private final String stationName;

  public StationNameNotFoundException(String stationName) {
    this.stationName = stationName;
  }

  @Override
  public String getMessage() {
    return String.format("Station %s is not found", stationName);
  }
}