package com.example.routing.component;

import com.example.routing.model.Station;
import com.example.routing.repository.StationRepository;
import com.example.routing.service.StationService;
import com.example.routing.service.impl.StationServiceImpl;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TestStationDataLoader {
  @Autowired
  private StationRepository stationRepository;
  
  private StationService stationService;
  private StationDataLoader stationDataLoader;

  @Before
  public void setUp() {
    this.stationService = new StationServiceImpl(stationRepository);
    this.stationDataLoader = new StationDataLoader(stationService);
  }
  
  @Test
  public void testStationDataLoader_stationsLoaded() {
    stationDataLoader.loadStationsIntoH2Database("StationMap.csv");

    List<Station> stations = stationService.getAllStations();
    Assert.assertFalse(stations.isEmpty());
  }

  @Test
  public void testStationDataLoader_exception() {
    stationDataLoader.loadStationsIntoH2Database("asd.csv");

    List<Station> stations = stationService.getAllStations();
    Assert.assertTrue(stations.isEmpty());
  }
}
