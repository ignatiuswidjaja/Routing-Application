package com.example.routing.service;

import com.example.routing.model.Station;
import com.example.routing.model.request.GetShortestRouteSpec;
import com.example.routing.model.request.GetShortestRouteWithTimeSpec;
import java.util.List;

public interface RouteService {
  List<Station> getShortestRoute(GetShortestRouteSpec spec);

  List<Station> getShortestRouteWithTime(GetShortestRouteWithTimeSpec spec);
}
