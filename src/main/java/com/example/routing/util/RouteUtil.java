package com.example.routing.util;

import com.example.routing.model.Station;
import com.example.routing.model.enums.TimePeriod;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RouteUtil {
  public static int calculateTravelCost(Station from, Station to, DateTime dateTime) {
    TimePeriod timePeriod = DateUtil.convertDateTimeToTimePeriod(dateTime);
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
    if(from.getStationLine().equals("TE")) {
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
}
