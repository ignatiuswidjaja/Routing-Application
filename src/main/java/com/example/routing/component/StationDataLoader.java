package com.example.routing.component;

import com.example.routing.model.Station;
import com.example.routing.service.StationService;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import java.io.File;
import java.text.SimpleDateFormat;
import javax.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Slf4j
@AllArgsConstructor
@Component
public class StationDataLoader {
  private final StationService stationService;

  @PostConstruct
  private void setUpData() {
    loadStationsIntoH2Database("StationMap.csv");
  }

  public void loadStationsIntoH2Database(String fileName) {
    try {
      CsvMapper mapper = new CsvMapper();
      mapper.registerModule(new JodaModule());
      mapper.setDateFormat(new SimpleDateFormat("d MMMM yyyy"));
      
      CsvSchema schema = CsvSchema.emptySchema().withHeader();

      File file = new ClassPathResource(fileName).getFile();
      MappingIterator<Station> iterator = mapper.readerFor(Station.class).with(schema).readValues(file);
      log.info("Loading station data from file to database...");
      while(iterator.hasNext()) {
        Station station = iterator.next();
        station = station.toBuilder()
            .stationLine(station.getStationCode().replaceAll("[0-9]", ""))
            .stationNumber(Integer.parseInt(station.getStationCode().replaceAll("[A-Z]", "")))
            .build();
        stationService.addStation(station);
      }
      log.info("Loading station data finished");
    } catch (Exception e) {
      log.error("Error while loading objects from CSV file: {}", fileName, e);
    }
  }
}
