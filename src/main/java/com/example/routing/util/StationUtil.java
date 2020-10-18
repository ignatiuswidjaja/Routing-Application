package com.example.routing.util;

import com.example.routing.model.Station;
import com.example.routing.model.StationEntity;
import com.example.routing.model.enums.TimePeriod;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class StationUtil {
  public static List<Station> convertStationEntities(List<StationEntity> stationEntities) {
    List<Station> stations = new ArrayList<>();
    for (StationEntity stationEntity : stationEntities) {
      stations.add(convertStationEntity(stationEntity));
    }
    return stations;
  }

  public static List<StationEntity> convertStations(List<Station> stations) {
    List<StationEntity> stationEntities = new ArrayList<>();
    for (Station station : stations) {
      stationEntities.add(convertStation(station));
    }
    return stationEntities;
  }

  public static Station convertStationEntity(StationEntity stationEntity) {
    return Station.builder()
        .stationCode(stationEntity.getStationCode())
        .stationName(stationEntity.getStationName())
        .openingDate(new DateTime(stationEntity.getOpeningTimestamp()))
        .stationLine(stationEntity.getStationLine())
        .stationNumber(stationEntity.getStationNumber())
        .build();
  }

  public static StationEntity convertStation(Station station) {
    return StationEntity.builder()
        .stationCode(station.getStationCode())
        .stationName(station.getStationName())
        .openingTimestamp(station.getOpeningDate().getMillis())
        .stationLine(station.getStationLine())
        .stationNumber(station.getStationNumber())
        .build();
  }

  public static boolean isOpen(Station station, DateTime dateTime) {
    if (dateTime == null) {
      return true;
    }

    TimePeriod timePeriod = DateTimeUtil.convertDateTimeToTimePeriod(dateTime);

    // check if station is closed
    if (timePeriod == TimePeriod.NIGHT) {
      switch (station.getStationLine()) {
        case "DT":
        case "CG":
        case "CE":
          return false;
        default:
          break;
      }
    }

    return station.getOpeningDate().isBefore(dateTime);
  }
}
