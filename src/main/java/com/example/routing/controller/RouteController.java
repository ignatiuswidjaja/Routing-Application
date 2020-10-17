package com.example.routing.controller;

import com.example.routing.repository.StationRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/route")
public class RouteController {
  private final StationRepository stationRepository;

  @GetMapping("/shortest")
  public String getShortestRouteWithoutTime() {
    return "";
  }

  @GetMapping("/shortest/time")
  public String getShortestRouteWithTime() {
    return "";
  }
}
