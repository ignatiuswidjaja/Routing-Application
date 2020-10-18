package com.example.routing.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TimePeriod {
  PEAK("peak hours"),
  NIGHT("night"),
  OTHER("off-peak hours");

  private final String alias;
}
