package com.example.routing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("com.example.routing.repository")
@EntityScan("com.example.routing.model")
@SpringBootApplication
public class RoutingApplication {
  public static void main(String[] args) {
    SpringApplication.run(RoutingApplication.class, args);
  }
}
