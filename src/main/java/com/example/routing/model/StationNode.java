package com.example.routing.model;

import java.util.Objects;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StationNode {
  private Station current;
  private Station previous;
  private int travelCost;
  private int estimatedCost;

  public StationNode(Station current) {
    this.current = current;
    this.previous = null;
    this.travelCost = 0;
    this.estimatedCost = Integer.MAX_VALUE;
  }

  public StationNode(Station current, Station previous, int travelCost, int estimatedCost) {
    this.current = current;
    this.previous = previous;
    this.travelCost = travelCost;
    this.estimatedCost = estimatedCost;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof StationNode)) {
      return false;
    }
    StationNode that = (StationNode) o;
    return this.current.getStationCode().equals(that.current.getStationCode());
  }

  @Override
  public int hashCode() {
    return Objects.hash(current, previous, travelCost, estimatedCost);
  }
}
