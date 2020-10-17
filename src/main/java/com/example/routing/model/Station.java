package com.example.routing.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder(builderClassName = "Builder", toBuilder = true)
@Getter
public class Station {
  private String stationCode;
  private String stationName;
  @JsonFormat(pattern = "d MMMM yyyy")
  private DateTime openingDate;
  private String stationLine;
  private int stationNumber;
}
