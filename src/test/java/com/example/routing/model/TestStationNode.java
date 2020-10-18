package com.example.routing.model;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class TestStationNode {
  @Test
  public void testStationNodeConstructor() {
    Station current = Station.builder().build();
    Station previous = Station.builder().build();
    int travelCost = 1;
    int estimatedCost = 2;

    // test current station argument constructor
    StationNode stationNode = new StationNode(current);

    Assert.assertEquals(current, stationNode.getCurrent());
    Assert.assertNull(stationNode.getPrevious());
    Assert.assertEquals(0, stationNode.getTravelCost());
    Assert.assertEquals(Integer.MAX_VALUE, stationNode.getEstimatedCost());

    // test all arguments constructor
    stationNode = new StationNode(current, previous, travelCost, estimatedCost);

    Assert.assertEquals(current, stationNode.getCurrent());
    Assert.assertEquals(previous, stationNode.getPrevious());
    Assert.assertEquals(travelCost, stationNode.getTravelCost());
    Assert.assertEquals(estimatedCost, stationNode.getEstimatedCost());
  }

  @Test
  public void testStationNodeEquals() {
    Station current = Station.builder()
        .stationCode("NS1")
        .build();
    Station next = Station.builder()
        .stationCode("NS2")
        .build();
    Station duplicateCurrent = Station.builder()
        .stationCode(current.getStationCode())
        .build();

    StationNode currentStationNode = new StationNode(current, null, 1, 1);
    StationNode nextStationNode = new StationNode(next, current, 1, 1);
    StationNode duplicateCurrentStationNode = new StationNode(duplicateCurrent, duplicateCurrent, 1, 1);

    Assert.assertEquals(currentStationNode, currentStationNode);
    Assert.assertNotEquals(currentStationNode, current);
    Assert.assertNotEquals(currentStationNode, nextStationNode);
    Assert.assertEquals(currentStationNode, duplicateCurrentStationNode);
  }
}
