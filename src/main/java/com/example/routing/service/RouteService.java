package com.example.routing.service;

import com.example.routing.model.request.GetShortestRouteSpec;
import com.example.routing.model.request.GetShortestRouteWithTimeSpec;
import java.util.List;

public interface RouteService {
  List<String> getShortestRoute(GetShortestRouteSpec spec);

  List<String> getShortestRouteWithTime(GetShortestRouteWithTimeSpec spec);
}
