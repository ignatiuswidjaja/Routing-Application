package com.example.routing.component;

import com.example.routing.model.StationEntity;
import com.example.routing.repository.StationRepository;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TestStationConfigLoader {
  @Autowired
  private StationRepository stationRepository;

  @Test
  public void testStationConfigLoader_stationsLoaded() {
    StationConfigLoader stationConfigLoader = new StationConfigLoader(stationRepository);
    stationConfigLoader.loadStationsIntoH2Database("StationMap.csv");

    List<StationEntity> stationEntities = stationRepository.findAll();
    Assert.assertFalse(stationEntities.isEmpty());

  }

  @Test
  public void testStationConfigLoader_exception() {
    StationConfigLoader stationConfigLoader = new StationConfigLoader(stationRepository);
    stationConfigLoader.loadStationsIntoH2Database("asd.csv");

    List<StationEntity> stationEntities = stationRepository.findAll();
    Assert.assertTrue(stationEntities.isEmpty());
  }
}
