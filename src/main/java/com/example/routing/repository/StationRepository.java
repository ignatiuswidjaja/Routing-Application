package com.example.routing.repository;

import com.example.routing.model.StationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StationRepository extends JpaRepository<StationEntity, String> {
}
