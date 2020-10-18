package com.example.routing.model.exception;

public class StationCodeNotFoundException extends RuntimeException {
  private final String stationCode;

  public StationCodeNotFoundException(String stationCode) {
    this.stationCode = stationCode;
  }

  @Override
  public String getMessage() {
    return String.format("Station with code %s is not found", stationCode);
  }
}
