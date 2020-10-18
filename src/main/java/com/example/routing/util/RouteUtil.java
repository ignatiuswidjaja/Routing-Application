package com.example.routing.util;

import com.example.routing.model.Station;
import com.example.routing.model.enums.TimePeriod;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RouteUtil {
  private static final String NEW_LINE = "\n";

  public static int calculateTravelCost(Station from, Station to, DateTime dateTime) {
    TimePeriod timePeriod = DateTimeUtil.convertDateTimeToTimePeriod(dateTime);
    switch (timePeriod) {
      case PEAK:
        return calculatePeakTravelCost(from, to);
      case NIGHT:
        return calculateNightTravelCost(from, to);
      case OTHER:
        return calculateOtherTravelCost(from, to);
      default:
        throw new IllegalArgumentException(String.format("Time period %s is invalid", timePeriod));
    }
  }

  private static int calculatePeakTravelCost(Station from, Station to) {
    if (!from.getStationLine().equals(to.getStationLine())) {
      return 15;
    }

    switch (from.getStationLine()) {
      case "NS":
      case "NE":
        return 12;
      default:
        return 10;
    }
  }

  private static int calculateNightTravelCost(Station from, Station to) {
    if (!from.getStationLine().equals(to.getStationLine())) {
      return 10;
    }

    if ("TE".equals(from.getStationLine())) {
      return 8;
    }
    return 10;
  }

  private static int calculateOtherTravelCost(Station from, Station to) {
    if (!from.getStationLine().equals(to.getStationLine())) {
      return 10;
    }

    switch (from.getStationLine()) {
      case "DT":
      case "TE":
        return 8;
      default:
        return 10;
    }
  }

  public static int calculateEstimatedCost(Station from, Station to) {
    // if it's stations is in a different line, we can't determine the heuristic value
    if (!from.getStationLine().equals(to.getStationLine())) {
      return Integer.MAX_VALUE;
    } else {
      return Math.abs(from.getStationNumber() - to.getStationNumber());
    }
  }

  public static String parseRoute(List<Station> stations, Integer totalTravelTimeInMinutes, TimePeriod timePeriod) {
    if (stations.isEmpty()) {
      return "";
    }

    StringBuilder sb = new StringBuilder();
    Station originStation = stations.get(0);
    Station destinationStation = stations.get(stations.size() - 1);

    // format travel from X to Y
    sb.append(String.format("Travel from %s to %s", originStation.getStationName(), destinationStation.getStationName()));
    if (timePeriod != null) {
      sb.append(" during ").append(timePeriod.getAlias());
    }
    sb.append(NEW_LINE);

    // format description
    if (totalTravelTimeInMinutes == null) {
      sb.append(String.format("Stations travelled: %s", stations.size() - 1)).append(NEW_LINE);
    } else {
      sb.append(String.format("Time: %s minutes", totalTravelTimeInMinutes)).append(NEW_LINE);
    }

    // format route
    String routeString = stations.stream()
        .map(station -> String.format("'%s'", station.getStationCode()))
        .collect(Collectors.joining(", ", "(", ")"));
    sb.append(String.format("Route: %s", routeString)).append(NEW_LINE);

    // format trip step
    for (int i = 1; i < stations.size(); i++) {
      // update destination station
      destinationStation = stations.get(i);

      String tripStep;
      if (originStation.getStationLine().equals(destinationStation.getStationLine())) {
        tripStep = String.format(
            "Take %s line from %s to %s",
            originStation.getStationLine(),
            originStation.getStationName(),
            destinationStation.getStationName()
        );
      } else {
        tripStep = String.format(
            "Change from %s line to %s line",
            originStation.getStationLine(),
            destinationStation.getStationLine()
        );
      }
      sb.append(NEW_LINE).append(tripStep);

      // update origin station using the current value of destination station
      originStation = destinationStation;
    }

    return sb.toString();
  }
}
