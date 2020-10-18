package com.example.routing.repository;

import com.example.routing.model.StationEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StationRepository extends JpaRepository<StationEntity, String> {
  StationEntity findByStationLineAndStationNumber(String stationLine, int stationNumber);
  List<StationEntity> findByStationName(String stationName);
}
