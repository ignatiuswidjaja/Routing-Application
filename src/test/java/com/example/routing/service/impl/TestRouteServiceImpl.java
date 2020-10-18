package com.example.routing.service.impl;

import com.example.routing.model.Station;
import com.example.routing.model.exception.RouteNotFoundException;
import com.example.routing.model.exception.StationNameNotFoundException;
import com.example.routing.model.result.ShortestRouteResult;
import com.example.routing.model.result.ShortestRouteWithTimeResult;
import com.example.routing.model.spec.GetShortestRouteSpec;
import com.example.routing.model.spec.GetShortestRouteWithTimeSpec;
import com.example.routing.service.RouteService;
import com.example.routing.service.StationService;
import java.util.List;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestRouteServiceImpl {
  @Autowired
  private StationService stationService;
  @Autowired
  private RouteService routeService;

  @Test
  public void testIntegrationShortestRoute_routeFound() {
    GetShortestRouteSpec spec = GetShortestRouteSpec.builder()
        .originStationName("Holland Village")
        .destinationStationName("Bugis")
        .build();

    ShortestRouteResult result = routeService.getShortestRoute(spec);
    List<Station> stations = result.getStations();

    Assert.assertNotNull(stations);
    Assert.assertFalse(stations.isEmpty());
    Assert.assertEquals(spec.getOriginStationName(), stations.get(0).getStationName());
    Assert.assertEquals(spec.getDestinationStationName(), stations.get(stations.size() - 1).getStationName());
  }

  @Test(expected = StationNameNotFoundException.class)
  public void testIntegrationShortestRoute_originStationNotFound() {
    GetShortestRouteSpec spec = GetShortestRouteSpec.builder()
        .originStationName("test")
        .destinationStationName("Bugis")
        .build();

    routeService.getShortestRoute(spec);
  }

  @Test(expected = StationNameNotFoundException.class)
  public void testIntegrationShortestRoute_destinationStationNotFound() {
    GetShortestRouteSpec spec = GetShortestRouteSpec.builder()
        .originStationName("Holland Village")
        .destinationStationName("test")
        .build();

    routeService.getShortestRoute(spec);
  }

  @Test
  public void testIntegrationShortestRouteWithTime_routeFound() {
    GetShortestRouteWithTimeSpec spec = GetShortestRouteWithTimeSpec.builder()
        .originStationName("Boon Lay")
        .destinationStationName("Little India")
        .departureTimestamp(new DateTime(2020, 10, 19, 8, 0))
        .build();

    ShortestRouteWithTimeResult result = routeService.getShortestRouteWithTime(spec);
    List<Station> stations = result.getStations();

    Assert.assertNotNull(stations);
    Assert.assertFalse(stations.isEmpty());
    Assert.assertEquals(spec.getOriginStationName(), stations.get(0).getStationName());
    Assert.assertEquals(spec.getDestinationStationName(), stations.get(stations.size() - 1).getStationName());
  }

  @Test(expected = RouteNotFoundException.class)
  public void testIntegrationShortestRouteWithTime_routeNotFound() {
    GetShortestRouteWithTimeSpec spec = GetShortestRouteWithTimeSpec.builder()
        .originStationName("Jurong East")
        .destinationStationName("Marina South")
        .departureTimestamp(new DateTime(2020, 10, 19, 12, 0))
        .build();

    routeService.getShortestRouteWithTime(spec);
  }
}
