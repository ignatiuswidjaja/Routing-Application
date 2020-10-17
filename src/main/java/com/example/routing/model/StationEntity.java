package com.example.routing.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import org.joda.time.DateTime;

@Entity
public class StationEntity {
  @Id
  private String stationId;

  @Column(nullable = false)
  private String stationName;

  @Column(nullable = false)
  private DateTime openingDate;
}
