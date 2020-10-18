package com.example.routing.model.spec;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder(builderClassName = "Builder")
@Getter
public class GetShortestRouteSpec {
  private final String originStationName;
  private final String destinationStationName;
}
