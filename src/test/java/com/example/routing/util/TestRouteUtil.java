package com.example.routing.util;

import com.example.routing.model.Station;
import com.example.routing.model.enums.TimePeriod;
import java.util.ArrayList;
import java.util.List;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class TestRouteUtil {
  @Test
  public void testCalculateTravelCost_withPeakDateTime() {
    Station nsStation = Station.builder()
        .stationLine("NS")
        .build();
    Station neStation = Station.builder()
        .stationLine("NE")
        .build();
    Station dtStation = Station.builder()
        .stationLine("DT")
        .build();
    DateTime dateTime = new DateTime(2020, 10, 16, 9, 0);

    Assert.assertEquals(15, RouteUtil.calculateTravelCost(nsStation, neStation, dateTime));
    Assert.assertEquals(12, RouteUtil.calculateTravelCost(nsStation, nsStation, dateTime));
    Assert.assertEquals(12, RouteUtil.calculateTravelCost(neStation, neStation, dateTime));
    Assert.assertEquals(10, RouteUtil.calculateTravelCost(dtStation, dtStation, dateTime));
  }

  @Test
  public void testCalculateTravelCost_withNightDateTime() {
    Station dtStation = Station.builder()
        .stationLine("DT")
        .build();
    Station teStation = Station.builder()
        .stationLine("TE")
        .build();
    DateTime dateTime = new DateTime(2020, 10, 16, 22, 0);

    Assert.assertEquals(10, RouteUtil.calculateTravelCost(teStation, dtStation, dateTime));
    Assert.assertEquals(8, RouteUtil.calculateTravelCost(teStation, teStation, dateTime));
    Assert.assertEquals(10, RouteUtil.calculateTravelCost(dtStation, dtStation, dateTime));
  }

  @Test
  public void testCalculateTravelCost_withOtherDateTime() {
    Station nsStation = Station.builder()
        .stationLine("NS")
        .build();
    Station dtStation = Station.builder()
        .stationLine("DT")
        .build();
    Station teStation = Station.builder()
        .stationLine("TE")
        .build();
    DateTime dateTime = null;

    Assert.assertEquals(10, RouteUtil.calculateTravelCost(nsStation, dtStation, dateTime));
    Assert.assertEquals(10, RouteUtil.calculateTravelCost(nsStation, nsStation, dateTime));
    Assert.assertEquals(8, RouteUtil.calculateTravelCost(dtStation, dtStation, dateTime));
    Assert.assertEquals(8, RouteUtil.calculateTravelCost(teStation, teStation, dateTime));
  }

  @Test
  public void testCalculateEstimatedCost() {
    Station ns1Station = Station.builder()
        .stationLine("NS")
        .stationNumber(1)
        .build();
    Station dt1Station = Station.builder()
        .stationLine("DT")
        .stationNumber(1)
        .build();
    Station dt13Station = Station.builder()
        .stationLine("DT")
        .stationNumber(13)
        .build();

    Assert.assertEquals(Integer.MAX_VALUE, RouteUtil.calculateEstimatedCost(ns1Station, dt1Station));
    Assert.assertEquals(12, RouteUtil.calculateEstimatedCost(dt1Station, dt13Station));
  }

  @Test
  public void testParseRoute_withEmptyStations() {
    Assert.assertEquals("", RouteUtil.parseRoute(new ArrayList<>(), null, null));
  }

  @Test
  public void testParseRoute_withoutAndWithTime() {
    List<Station> stations = new ArrayList<>();
    stations.add(Station.builder()
        .stationName("First")
        .stationLine("A")
        .build()
    );
    stations.add(Station.builder()
        .stationName("Second")
        .stationLine("A")
        .build()
    );
    stations.add(Station.builder()
        .stationName("Third")
        .stationLine("B")
        .build()
    );
    stations.add(Station.builder()
        .stationName("Fourth")
        .stationLine("B")
        .build()
    );
    Assert.assertFalse(RouteUtil.parseRoute(stations, null, null).isEmpty());
    
    Assert.assertFalse(RouteUtil.parseRoute(stations, 100, TimePeriod.PEAK).isEmpty());
    Assert.assertFalse(RouteUtil.parseRoute(stations, 100, TimePeriod.NIGHT).isEmpty());
    Assert.assertFalse(RouteUtil.parseRoute(stations, 100, TimePeriod.OTHER).isEmpty());
  }
}
