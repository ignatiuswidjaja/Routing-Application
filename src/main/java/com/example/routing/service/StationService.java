package com.example.routing.service;

import com.example.routing.model.Station;
import java.util.List;

public interface StationService {
  void addStation(Station station);
  Station getStationByStationCode(String stationCode);
  Station getStationByStationLineAndStationNumber(String stationLine, int stationNumber);
  List<Station> getAllStations();
  List<Station> getStationsByStationName(String stationName);
}
