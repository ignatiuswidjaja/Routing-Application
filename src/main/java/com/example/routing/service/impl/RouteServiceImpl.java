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
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class RouteServiceImpl implements RouteService {
  private static final int MAX_STATIONS_IN_A_LINE = 50;

  private final StationService stationService;

  private final List<Station> stations;
  private final Map<String, List<Station>> stationNetwork;

  public RouteServiceImpl(StationService stationService) {
    this.stationService = stationService;

    this.stations = stationService.getAllStations();
    this.stationNetwork = new HashMap<>();

    initStationNetwork();
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

  //===================
  // PRIVATE FUNCTIONS
  //===================
  private void initStationNetwork() {
    for (Station station : stations) {
      List<Station> adjacentStations = new ArrayList<>();

      // find connected transit station
      List<Station> transitStations = stationService.getStationsByStationName(station.getStationName())
          .stream()
          .filter(transit -> !transit.getStationCode().equals(station.getStationCode()))
          .collect(Collectors.toList());
      adjacentStations.addAll(transitStations);

      String stationLine = station.getStationLine();

      // find previous station in line
      Station adjacentStation = null;
      int stationNumber = station.getStationNumber() - 1;
      while (adjacentStation == null && stationNumber > 0) {
        adjacentStation = stationService.getStationByStationLineAndStationNumber(stationLine, stationNumber);
        stationNumber--;
      }
      if (adjacentStation != null) {
        adjacentStations.add(adjacentStation);
      }

      // find next station in line
      adjacentStation = null;
      stationNumber = station.getStationNumber() + 1;
      while (adjacentStation == null && stationNumber <= MAX_STATIONS_IN_A_LINE) {
        adjacentStation = stationService.getStationByStationLineAndStationNumber(stationLine, stationNumber);
        stationNumber++;
      }
      if (adjacentStation != null) {
        adjacentStations.add(adjacentStation);
      }

      // register in connected stations
      stationNetwork.put(station.getStationName(), adjacentStations);
    }
  }
}
