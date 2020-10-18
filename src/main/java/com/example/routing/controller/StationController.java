package com.example.routing.controller;

import com.example.routing.model.Station;
import com.example.routing.service.StationService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/station")
public class StationController {
  private final StationService stationService;

  @GetMapping
  public List<Station> getAllStations() {
    return stationService.getAllStations();
  }

  @GetMapping("/{stationCode}")
  public Station getStationByStationCode(@PathVariable String stationCode) {
    return stationService.getStationByStationCode(stationCode);
  }

  @GetMapping("/name/{stationName}")
  public List<Station> getStationsByStationName(@PathVariable String stationName) {
    return stationService.getStationsByStationName(stationName);
  }
}
