package com.example.routing.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder(builderClassName = "Builder")
@Getter
@Entity
public class StationEntity {
  @Id
  private String stationCode;

  @Column(nullable = false)
  private String stationName;

  @Column(nullable = false)
  private long openingDate;

  @Column(nullable = false)
  private String stationLine;

  @Column(nullable = false)
  private int stationNumber;
}
