package com.example.routing.service.impl;

import com.example.routing.model.Station;
import com.example.routing.model.request.GetShortestRouteSpec;
import com.example.routing.model.request.GetShortestRouteWithTimeSpec;
import com.example.routing.service.RouteService;
import com.example.routing.service.StationService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class RouteServiceImpl implements RouteService {
  private final StationService stationService;

  private final List<Station> stations;
  private final Map<String, List<String>> stationNameMap;

  public RouteServiceImpl(StationService stationService) {
    this.stationService = stationService;
    this.stations = stationService.getAllStations();
    this.stationNameMap = new HashMap<>();

    // map station name to station codes
    for (Station station : stations) {
      List<String> stationCodes = stationNameMap.get(station.getStationName());
      if (stationCodes == null) {
        stationCodes = new ArrayList<>();
      }
      stationCodes.add(station.getStationCode());

      stationNameMap.put(station.getStationName(), stationCodes);
    }
    System.out.println("asd");
  }

  @Override
  public List<String> getShortestRoute(GetShortestRouteSpec spec) {
    return null;  // TODO impl
  }

  @Override
  public List<String> getShortestRouteWithTime(GetShortestRouteWithTimeSpec spec) {
    return null;  // TODO impl
  }
}
