package com.example.routing.util;

import com.example.routing.model.Station;
import com.example.routing.model.StationEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class StationUtil {
  public static Station convertStationEntity(StationEntity stationEntity) {
    return Station.builder()
        .stationCode(stationEntity.getStationCode())
        .stationName(stationEntity.getStationName())
        .openingDate(new DateTime(stationEntity.getOpeningDate()))
        .build();
  }

  public static StationEntity convertStation(Station station) {
    return StationEntity.builder()
        .stationCode(station.getStationCode())
        .stationName(station.getStationName())
        .openingDate(station.getOpeningDate().getMillis())
        .build();
  }
}
