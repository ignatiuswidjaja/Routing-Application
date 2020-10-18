package com.example.routing.model.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class StationCodeNotFoundException extends BaseNotFoundException {
  private final String stationCode;

  @Override
  public String getMessage() {
    return String.format("Station with code %s is not found", stationCode);
  }
}
