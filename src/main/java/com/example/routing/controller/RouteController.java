package com.example.routing.controller;

import com.example.routing.model.Station;
import com.example.routing.model.result.ShortestRouteWithTimeResult;
import com.example.routing.model.spec.GetShortestRouteSpec;
import com.example.routing.model.spec.GetShortestRouteWithTimeSpec;
import com.example.routing.repository.StationRepository;
import com.example.routing.service.RouteService;
import com.example.routing.util.DateUtil;
import java.util.List;
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
  public List<String> getShortestRoute(@RequestParam(name = "orig") String originStationName,
                                       @RequestParam(name = "dest") String destinationStationName) {
    GetShortestRouteSpec spec = GetShortestRouteSpec.builder()
        .originStationName(originStationName)
        .destinationStationName(destinationStationName)
        .build();

    List<Station> stations = routeService.getShortestRoute(spec);
    return null;
  }

  @GetMapping("/time")
  public List<String> getShortestRouteWithTime(@RequestParam(name = "orig") String originStationName,
                                               @RequestParam(name = "dest") String destinationStationName,
                                               @RequestParam(name = "time") String departureTimestampString) throws Exception {
    DateTime departureTimestamp = DateUtil.convertStringToDateTime(departureTimestampString);

    GetShortestRouteWithTimeSpec spec = GetShortestRouteWithTimeSpec.builder()
        .originStationName(originStationName)
        .destinationStationName(destinationStationName)
        .departureTimestamp(departureTimestamp)
        .build();

    ShortestRouteWithTimeResult result = routeService.getShortestRouteWithTime(spec);
    return null;
  }
}
