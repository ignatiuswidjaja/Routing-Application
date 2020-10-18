package com.example.routing.service;

import com.example.routing.model.Station;
import java.util.List;

public interface StationService {
  void addStation(Station station);
  List<Station> getAllStations();
  Station getStationByStationCode(String stationCode);
  List<Station> getStationsByStationName(String stationName);
}
