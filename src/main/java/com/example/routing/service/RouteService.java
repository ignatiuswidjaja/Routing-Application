package com.example.routing.service;

import com.example.routing.model.Station;
import com.example.routing.model.result.ShortestRouteWithTimeResult;
import com.example.routing.model.spec.GetShortestRouteSpec;
import com.example.routing.model.spec.GetShortestRouteWithTimeSpec;
import java.util.List;

public interface RouteService {
  List<Station> getShortestRoute(GetShortestRouteSpec spec);

  ShortestRouteWithTimeResult getShortestRouteWithTime(GetShortestRouteWithTimeSpec spec);
}
