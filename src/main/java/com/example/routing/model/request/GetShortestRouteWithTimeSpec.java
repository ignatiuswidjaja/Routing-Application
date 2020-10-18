package com.example.routing.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder(builderClassName = "Builder")
@Getter
public class GetShortestRouteWithTimeSpec {
  private final String originStationName;
  private final String destinationStationName;
  private final DateTime departureDate;
}
