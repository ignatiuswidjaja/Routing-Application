package com.example.routing.service.impl;

import com.example.routing.model.Station;
import com.example.routing.repository.StationRepository;
import com.example.routing.service.StationService;
import java.util.List;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TestStationServiceImpl {
  @Autowired
  private StationRepository stationRepository;

  private StationService stationService;
  
  @Before
  public void setUp() {
    this.stationService = new StationServiceImpl(stationRepository);
  }

  @Test
  public void testIntegrationStationService() {
    // assert that no stations are in db
    Assert.assertTrue(stationService.getAllStations().isEmpty());

    // add station NS1
    Station ns1Station = Station.builder()
        .stationCode("NS1")
        .stationName("Jurong")
        .openingDate(new DateTime())
        .stationLine("NS")
        .stationNumber(1)
        .build();
    stationService.addStation(ns1Station);

    // retrieve all stations and assert
    List<Station> stations = stationService.getAllStations();
    Assert.assertEquals(1, stations.size());
    Assert.assertEquals(ns1Station.getStationCode(), stations.get(0).getStationCode());
    Assert.assertEquals(ns1Station.getStationName(), stations.get(0).getStationName());
    Assert.assertEquals(ns1Station.getOpeningDate().getMillis(), stations.get(0).getOpeningDate().getMillis());
    Assert.assertEquals(ns1Station.getStationLine(), stations.get(0).getStationLine());
    Assert.assertEquals(ns1Station.getStationNumber(), stations.get(0).getStationNumber());

    // add transit station TS2
    Station ts2Station = ns1Station.toBuilder()
        .stationCode("TS2")
        .stationLine("TS")
        .stationNumber(2)
        .build();
    stationService.addStation(ts2Station);

    // get station by station line and number, and assert
    Station station = stationService.getStationByStationLineAndStationNumber(
        ns1Station.getStationLine(),
        ns1Station.getStationNumber()
    );
    Assert.assertEquals(ns1Station.getStationCode(), station.getStationCode());
    Assert.assertEquals(ns1Station.getStationName(), station.getStationName());
    Assert.assertEquals(ns1Station.getOpeningDate().getMillis(), station.getOpeningDate().getMillis());
    Assert.assertEquals(ns1Station.getStationLine(), station.getStationLine());
    Assert.assertEquals(ns1Station.getStationNumber(), station.getStationNumber());

    // get station by station name, assert that there are 2 stations in the list
    stations = stationService.getStationsByStationName(ns1Station.getStationName());
    Assert.assertEquals(2, stations.size());

    // get NS1 station by station code, assert that station codes are the same
    station = stationService.getStationByStationCode(ns1Station.getStationCode());
    Assert.assertEquals(ns1Station.getStationCode(), station.getStationCode());

    // get TS2 station by station code, assert that station codes are the same
    station = stationService.getStationByStationCode(ts2Station.getStationCode());
    Assert.assertEquals(ts2Station.getStationCode(), station.getStationCode());

    // get station with random line and number that does not exist, assert station is null
    station = stationService.getStationByStationLineAndStationNumber("ABC", 1);
    Assert.assertNull(station);
  }
}
