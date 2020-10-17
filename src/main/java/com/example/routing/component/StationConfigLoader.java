package com.example.routing.component;

import com.example.routing.model.Station;
import com.example.routing.repository.StationRepository;
import com.example.routing.util.StationUtil;
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
public class StationConfigLoader {
  private final StationRepository stationRepository;

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
      while(iterator.hasNext()) {
        Station station = iterator.next();
        stationRepository.save(StationUtil.convertStation(station));
      }
    } catch (Exception e) {
      log.error("Error while loading objects from CSV file: {}", fileName, e);
    }
  }
}
