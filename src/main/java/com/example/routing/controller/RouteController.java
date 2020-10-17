package com.example.routing.controller;

import com.example.routing.repository.StationRepository;
import com.example.routing.util.DateUtil;
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

  @GetMapping()
  public List<String> getShortestRoute(@RequestParam(name = "orig") String originStationName,
                                       @RequestParam(name = "dest") String destinationStationName) {
    return new ArrayList<>();
  }

  @GetMapping("/time")
  public List<String> getShortestRouteWithTime(@RequestParam(name = "orig") String originStationName,
                                               @RequestParam(name = "dest") String destinationStationName,
                                               @RequestParam(name = "time") String dateTimeString) throws Exception {
    DateTime dateTime = DateUtil.convertStringToDateTime(dateTimeString);

    return new ArrayList<>();
  }
}
