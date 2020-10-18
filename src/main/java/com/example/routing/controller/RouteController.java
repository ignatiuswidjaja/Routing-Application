package com.example.routing.controller;

import com.example.routing.model.Station;
import com.example.routing.model.StationEntity;
import com.example.routing.model.exception.StationCodeNotFoundException;
import com.example.routing.model.request.GetShortestRouteSpec;
import com.example.routing.model.request.GetShortestRouteWithTimeSpec;
import com.example.routing.repository.StationRepository;
import com.example.routing.service.RouteService;
import com.example.routing.util.DateUtil;
import com.example.routing.util.StationUtil;
import java.util.ArrayList;
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

    return routeService.getShortestRoute(spec);
  }

  @GetMapping("/time")
  public List<String> getShortestRouteWithTime(@RequestParam(name = "orig") String originStationName,
                                               @RequestParam(name = "dest") String destinationStationName,
                                               @RequestParam(name = "time") String dateTimeString) throws Exception {
    DateTime dateTime = DateUtil.convertStringToDateTime(dateTimeString);

    GetShortestRouteWithTimeSpec spec = GetShortestRouteWithTimeSpec.builder()
        .originStationName(originStationName)
        .destinationStationName(destinationStationName)
        .departureDate(dateTime)
        .build();

    return routeService.getShortestRouteWithTime(spec);
  }
}
