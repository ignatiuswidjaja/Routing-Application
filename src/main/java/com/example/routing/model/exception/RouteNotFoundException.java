package com.example.routing.model.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RouteNotFoundException extends BaseNotFoundException {
  private final String originStationName;
  private final String destinationStationName;
  
  @Override
  public String getMessage() {
    return String.format("Route from %s to %s is not found", originStationName, destinationStationName);
  }
}
