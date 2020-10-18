package com.example.routing.controller;

import com.example.routing.model.Station;
import com.example.routing.model.exception.StationCodeNotFoundException;
import com.example.routing.model.exception.StationNameNotFoundException;
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

  @GetMapping()
  public List<Station> getAllStations() {
    return stationService.getAllStations();
  }

  @GetMapping("/{stationCode}")
  public Station getStationByStationCode(@PathVariable String stationCode) {
    Station station = stationService.getStationByStationCode(stationCode);
    if (station == null) {
      throw new StationCodeNotFoundException(stationCode);
    }
    return station;
  }

  @GetMapping("/name/{stationName}")
  public List<Station> getStationsByStationName(@PathVariable String stationName) {
    List<Station> stations = stationService.getStationsByStationName(stationName);
    if (stations.isEmpty()) {
      throw new StationNameNotFoundException(stationName);
    }
    return stations;
  }
}
