package com.example.routing.service.impl;

import com.example.routing.model.Station;
import com.example.routing.model.StationNode;
import com.example.routing.model.exception.RouteNotFoundException;
import com.example.routing.model.exception.StationNameNotFoundException;
import com.example.routing.model.request.GetShortestRouteSpec;
import com.example.routing.model.request.GetShortestRouteWithTimeSpec;
import com.example.routing.service.RouteService;
import com.example.routing.service.StationService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
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

    initStations();
  }

  @Override
  public List<Station> getShortestRoute(GetShortestRouteSpec spec) {
    final String originStationName = spec.getOriginStationName();
    final String destinationStationName = spec.getDestinationStationName();

    // get origin and destination stations
    List<Station> originStations = stationService.getStationsByStationName(originStationName);
    List<Station> destinationStations = stationService.getStationsByStationName(destinationStationName);

    // validate
    if (originStations.isEmpty()) {
      throw new StationNameNotFoundException(spec.getOriginStationName());
    } else if (destinationStations.isEmpty()) {
      throw new StationNameNotFoundException(spec.getDestinationStationName());
    }

    // init variable
    Queue<StationNode> openSet = new PriorityQueue<>();
    Map<String, StationNode> allNodes = new HashMap<>();

    // put origin stations to open set
    for (Station station : originStations) {
      openSet.add(new StationNode(station));
    }

    // A* algorithm
    while (!openSet.isEmpty()) {
      // retrieve node from queue
      StationNode node = openSet.poll();
      Station current = node.getCurrent();

      // check if we arrive at destination
      if (current.getStationName().equals(destinationStationName)) {
        // trace back the route
        List<Station> route = new ArrayList<>();
        while (node != null) {
          route.add(node.getCurrent());
          node = allNodes.get(node.getPrevious().getStationName());
        }
        return route;
      }

      // get adjacent stations
      List<Station> adjacentStations = stationNetwork.get(node.getCurrent());
      for (Station adjacentStation : adjacentStations) {
        // check if it's already found

        // if it's on a different line
        int pathScore = 2;


//        openSet.add(new StationNode(adjacentStation, node, 2));
      }

    }

    throw new RouteNotFoundException(originStationName, destinationStationName);
  }

  @Override
  public List<Station> getShortestRouteWithTime(GetShortestRouteWithTimeSpec spec) {
    return null;
  }

  //===================
  // PRIVATE FUNCTIONS
  //===================
  private void initStations() {
    for (Station station : stations) {
      final String stationLine = station.getStationLine();

      // find connected transit station
      List<Station> adjacentStations = stationService.getStationsByStationName(station.getStationName())
          .stream()
          .filter(transit -> !transit.getStationCode().equals(station.getStationCode()))
          .collect(Collectors.toList());

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
