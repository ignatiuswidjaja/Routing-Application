package com.example.routing.controller;

import com.example.routing.model.Station;
import com.example.routing.model.exception.StationNotFoundException;
import com.example.routing.repository.StationRepository;
import com.example.routing.util.StationUtil;
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
  private final StationRepository stationRepository;

  @GetMapping
  public List<Station> getAllStations() {
    return StationUtil.convertStationEntities(stationRepository.findAll());
  }

  @GetMapping("/{stationCode}")
  public Station getStationByStationCode(@PathVariable String stationCode) {
    return StationUtil.convertStationEntity(
        stationRepository.findById(stationCode).orElseThrow(StationNotFoundException::new)
    );
  }

  @GetMapping("/name/{stationName}")
  public List<Station> getStationsByStationName(@PathVariable String stationName) {
    return StationUtil.convertStationEntities(stationRepository.findByStationName(stationName));
  }
}
