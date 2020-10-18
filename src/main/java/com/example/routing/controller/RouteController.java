package com.example.routing.controller;

import com.example.routing.model.result.ShortestRouteResult;
import com.example.routing.model.result.ShortestRouteWithTimeResult;
import com.example.routing.model.spec.GetShortestRouteSpec;
import com.example.routing.model.spec.GetShortestRouteWithTimeSpec;
import com.example.routing.repository.StationRepository;
import com.example.routing.service.RouteService;
import com.example.routing.util.DateTimeUtil;
import com.example.routing.util.RouteUtil;
import lombok.AllArgsConstructor;
import org.joda.time.DateTime;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/route")
public class RouteController {
  private final StationRepository stationRepository;
  private final RouteService routeService;

  @GetMapping()
  public String getShortestRoute(@RequestParam(name = "orig") String originStationName,
                                       @RequestParam(name = "dest") String destinationStationName) {
    GetShortestRouteSpec spec = GetShortestRouteSpec.builder()
        .originStationName(originStationName)
        .destinationStationName(destinationStationName)
        .build();

    ShortestRouteResult result = routeService.getShortestRoute(spec);
    return RouteUtil.parseRoute(result.getStations(), null, null);
  }

  @GetMapping("/time")
  public String getShortestRouteWithTime(@RequestParam(name = "orig") String originStationName,
                                               @RequestParam(name = "dest") String destinationStationName,
                                               @RequestParam(name = "time") String departureTimestampString) throws Exception {
    DateTime departureTimestamp = DateTimeUtil.convertStringToDateTime(departureTimestampString);

    GetShortestRouteWithTimeSpec spec = GetShortestRouteWithTimeSpec.builder()
        .originStationName(originStationName)
        .destinationStationName(destinationStationName)
        .departureTimestamp(departureTimestamp)
        .build();

    ShortestRouteWithTimeResult result = routeService.getShortestRouteWithTime(spec);
    return RouteUtil.parseRoute(result.getStations(), result.getTotalTravelTimeInMinutes(), result.getTimePeriod());
  }
}
