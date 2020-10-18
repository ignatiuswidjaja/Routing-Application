package com.example.routing.service.impl;

import com.example.routing.model.Station;
import com.example.routing.model.StationNode;
import com.example.routing.model.exception.RouteNotFoundException;
import com.example.routing.model.exception.StationNameNotFoundException;
import com.example.routing.model.result.ShortestRouteResult;
import com.example.routing.model.result.ShortestRouteWithTimeResult;
import com.example.routing.model.spec.GetShortestRouteSpec;
import com.example.routing.model.spec.GetShortestRouteWithTimeSpec;
import com.example.routing.service.RouteService;
import com.example.routing.service.StationService;
import com.example.routing.util.DateTimeUtil;
import com.example.routing.util.RouteUtil;
import com.example.routing.util.StationUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

@Service
public class RouteServiceImpl implements RouteService {
  private static final int MAX_STATIONS_IN_A_LINE = 50;

  private final StationService stationService;

  private final List<Station> stations;
  private final Map<String, List<Station>> stationNetwork;
  private final Comparator<StationNode> stationNodeComparator;

  public RouteServiceImpl(StationService stationService) {
    this.stationService = stationService;

    this.stations = stationService.getAllStations();
    this.stationNetwork = new HashMap<>();
    this.stationNodeComparator = Comparator
        .comparingInt(StationNode::getTravelCost)
        .thenComparingInt(StationNode::getEstimatedCost);

    initStations();
  }

  @Override
  public ShortestRouteResult getShortestRoute(GetShortestRouteSpec spec) {
    ShortestRouteWithTimeResult result = getShortestRouteWithTime(GetShortestRouteWithTimeSpec.builder()
        .originStationName(spec.getOriginStationName())
        .destinationStationName(spec.getDestinationStationName())
        .departureTimestamp(null)
        .build()
    );

    return ShortestRouteResult.builder()
        .stations(result.getStations())
        .build();
  }

  @Override
  public ShortestRouteWithTimeResult getShortestRouteWithTime(GetShortestRouteWithTimeSpec spec) {
    final String originStationName = spec.getOriginStationName();
    final String destinationStationName = spec.getDestinationStationName();
    final DateTime departureTimestamp = spec.getDepartureTimestamp();

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
    List<StationNode> openList = new ArrayList<>();
    Map<String, StationNode> closedMap = new HashMap<>();

    // put origin stations to open set
    for (Station station : originStations) {
      StationNode stationNode = new StationNode(station);
      openList.add(stationNode);
    }

    // A* / dijkstra mixed algorithm
    while (!openList.isEmpty()) {
      // sort, retrieve and remove the first node
      openList.sort(stationNodeComparator);
      StationNode node = openList.get(0);
      openList.remove(0);
      Station currentStation = node.getCurrent();

      // add current node to the closed map
      closedMap.put(currentStation.getStationCode(), node);

      // check if we arrive at destination
      if (currentStation.getStationName().equals(destinationStationName)) {
        // get travel cost
        int travelCost = node.getTravelCost();

        // trace back the route
        List<Station> route = new ArrayList<>();
        while (node != null) {
          route.add(node.getCurrent());
          node = node.getPrevious() == null ? null : closedMap.get(node.getPrevious().getStationCode());
        }

        // reverse the route
        Collections.reverse(route);

        return ShortestRouteWithTimeResult.builder()
            .stations(route)
            .totalTravelTimeInMinutes(travelCost)
            .timePeriod(DateTimeUtil.convertDateTimeToTimePeriod(departureTimestamp))
            .build();
      }

      // loop through each adjacent stations
      List<Station> adjacentStations = stationNetwork.get(currentStation.getStationCode());
      for (Station adjacentStation : adjacentStations) {
        // check if station is closed or it's already in the closed map, ignore it
        if (!StationUtil.isOpen(adjacentStation, departureTimestamp)
            || closedMap.get(adjacentStation.getStationCode()) != null) {
          continue;
        }

        // calculate travel cost
        int accumulativeTravelCost = node.getTravelCost() + RouteUtil.calculateTravelCost(
            currentStation,
            adjacentStation,
            departureTimestamp
        );

        // check if adjacent node is in the open list
        Optional<StationNode> optionalAdjacentNode = openList.stream()
            .filter(stationNode -> stationNode.getCurrent().getStationName().equals(adjacentStation.getStationName()))
            .findFirst();
        StationNode adjacentNode;

        // if adjacent node is in the open list
        if (optionalAdjacentNode.isPresent()) {
          // retrieve adjacent node
          adjacentNode = optionalAdjacentNode.get();

          // if the new travel cost is lower, update the value
          if (accumulativeTravelCost < adjacentNode.getTravelCost()) {
            adjacentNode.setPrevious(currentStation);
            adjacentNode.setTravelCost(accumulativeTravelCost);
          }
        } else {
          // if it's a new adjacent node, calculate estimated cost
          int estimatedCost = Integer.MAX_VALUE;
          for (Station destinationStation : destinationStations) {
            estimatedCost = Math.min(estimatedCost, RouteUtil.calculateEstimatedCost(adjacentStation, destinationStation));
          }
          adjacentNode = new StationNode(adjacentStation, currentStation, accumulativeTravelCost, estimatedCost);

          // register adjacent node in the open list
          openList.add(adjacentNode);
        }
      }
    }

    throw new RouteNotFoundException(originStationName, destinationStationName);
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
      stationNetwork.put(station.getStationCode(), adjacentStations);
    }
  }
}
