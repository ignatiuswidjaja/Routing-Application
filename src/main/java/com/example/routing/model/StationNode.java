package com.example.routing.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class StationNode implements Comparable<StationNode> {
  private final Station current;
  private final Station previous;
  private final int travelCost;
  private final int estimatedCost;

  public StationNode(Station current) {
    this.current = current;
    this.previous = null;
    this.travelCost = Integer.MAX_VALUE;
    this.estimatedCost = Integer.MAX_VALUE;
  }

  public StationNode(Station current, Station previous, int travelCost) {
    this.current = current;
    this.previous = previous;
    this.travelCost = travelCost;
    this.estimatedCost = Integer.MAX_VALUE;
  }

  public StationNode(Station current, Station previous, int travelCost, int estimatedCost) {
    this.current = current;
    this.previous = previous;
    this.travelCost = travelCost;
    this.estimatedCost = estimatedCost;
  }

  @Override
  public int compareTo(StationNode stationNode) {
    return Integer.compare(this.estimatedCost, stationNode.estimatedCost);
  }
}
