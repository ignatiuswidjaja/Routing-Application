package com.example.routing.service.impl;

import com.example.routing.model.Station;
import com.example.routing.model.StationEntity;
import com.example.routing.model.exception.StationCodeNotFoundException;
import com.example.routing.repository.StationRepository;
import com.example.routing.service.StationService;
import com.example.routing.util.StationUtil;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class StationServiceImpl implements StationService {
  private final StationRepository stationRepository;

  @Override
  public void addStation(Station station) {
    stationRepository.save(StationUtil.convertStation(station));
  }

  @Override
  public List<Station> getAllStations() {
    return StationUtil.convertStationEntities(stationRepository.findAll());
  }

  @Override
  public Station getStationByStationCode(String stationCode) {
    StationEntity stationEntity = stationRepository
        .findById(stationCode)
        .orElseThrow(() -> new StationCodeNotFoundException(stationCode));
    
    return StationUtil.convertStationEntity(stationEntity);
  }

  @Override
  public List<Station> getStationsByStationName(String stationName) {
    return StationUtil.convertStationEntities(stationRepository.findByStationName(stationName));
  }
}
