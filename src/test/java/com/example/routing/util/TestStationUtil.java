package com.example.routing.util;

import com.example.routing.model.Station;
import com.example.routing.model.StationEntity;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class TestStationUtil {
  @Test
  public void testConvertStationList_isSuccessful() {
    DateTime dateTime = new DateTime();
    List<Station> stations = new ArrayList<>();
    stations.add(Station.builder()
        .stationCode("A1")
        .stationName("ASD")
        .openingDate(dateTime)
        .stationLine("A")
        .stationNumber(123)
        .build());
    stations.add(Station.builder()
        .stationCode("A2")
        .stationName("ASDF")
        .openingDate(dateTime)
        .stationLine("B")
        .stationNumber(1234)
        .build());

    List<StationEntity> stationEntities = StationUtil.convertStations(stations);
    Assert.assertEquals(stations.size(), stationEntities.size());
    for (int i = 0; i < stations.size(); i++) {
      Station station = stations.get(i);
      StationEntity stationEntity = stationEntities.get(i);
      Assert.assertEquals(station.getStationCode(), stationEntity.getStationCode());
      Assert.assertEquals(station.getStationName(), stationEntity.getStationName());
      Assert.assertEquals(station.getOpeningDate().getMillis(), stationEntity.getOpeningTimestamp());
      Assert.assertEquals(station.getStationLine(), stationEntity.getStationLine());
      Assert.assertEquals(station.getStationNumber(), stationEntity.getStationNumber());
    }
  }

  @Test
  public void testConvertStationEntityList_isSuccessful() {
    DateTime dateTime = new DateTime();
    List<StationEntity> stationEntities = new ArrayList<>();
    stationEntities.add(StationEntity.builder()
        .stationCode("A1")
        .stationName("ASD")
        .openingTimestamp(dateTime.getMillis())
        .stationLine("A")
        .stationNumber(123)
        .build());
    stationEntities.add(StationEntity.builder()
        .stationCode("A2")
        .stationName("ASDF")
        .openingTimestamp(dateTime.getMillis())
        .stationLine("B")
        .stationNumber(1234)
        .build());

    List<Station> stations = StationUtil.convertStationEntities(stationEntities);
    Assert.assertEquals(stationEntities.size(), stations.size());
    for (int i = 0; i < stationEntities.size(); i++) {
      StationEntity stationEntity = stationEntities.get(i);
      Station station = stations.get(i);
      Assert.assertEquals(stationEntity.getStationCode(), station.getStationCode());
      Assert.assertEquals(stationEntity.getStationName(), station.getStationName());
      Assert.assertEquals(stationEntity.getOpeningTimestamp(), station.getOpeningDate().getMillis());
      Assert.assertEquals(stationEntity.getStationLine(), station.getStationLine());
      Assert.assertEquals(stationEntity.getStationNumber(), station.getStationNumber());
    }
  }

  @Test
  public void testConvertEmptyList_isSuccessful() {
    List<Station> stations = new ArrayList<>();
    List<StationEntity> convertedStationEntities = StationUtil.convertStations(stations);
    Assert.assertTrue(convertedStationEntities.isEmpty());

    List<StationEntity> stationEntities = new ArrayList<>();
    List<Station> convertedStations = StationUtil.convertStationEntities(stationEntities);
    Assert.assertTrue(convertedStations.isEmpty());

  }

  @Test
  public void testIsOpen_withNullDateTime() {
    Assert.assertTrue(StationUtil.isOpen(new Station(), null));
  }

  @Test
  public void testIsClosed_withNightDateTime() {
    DateTime dateTime = new DateTime().withTime(new LocalTime(23, 0));
    Station station = Station.builder()
        .stationLine("DT")
        .build();
    
    Assert.assertFalse(StationUtil.isOpen(station, dateTime));
  }

  @Test
  public void testIsOpen_withNightDateTimeAndAfterOpeningDate() {
    DateTime dateTime = new DateTime().withTime(new LocalTime(23, 0));
    Station station = Station.builder()
        .stationLine("NS")
        .openingDate(dateTime.minusDays(1))
        .build();

    Assert.assertTrue(StationUtil.isOpen(station, dateTime));
  }

  @Test
  public void testIsOpen_withNightDateTimeAndBeforeOpeningDate() {
    DateTime dateTime = new DateTime().withTime(new LocalTime(23, 0));
    Station station = Station.builder()
        .stationLine("NS")
        .openingDate(dateTime.plusDays(1))
        .build();

    Assert.assertFalse(StationUtil.isOpen(station, dateTime));
  }
}
