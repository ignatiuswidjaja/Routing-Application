package com.example.routing.model.request;

import com.example.routing.model.Station;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder(builderClassName = "Builder")
@Getter
public class GetShortestRouteSpec {
  private final Station origin;
  private final Station destination;
}
