package com.example.routing.service;

import com.example.routing.model.result.ShortestRouteResult;
import com.example.routing.model.result.ShortestRouteWithTimeResult;
import com.example.routing.model.spec.GetShortestRouteSpec;
import com.example.routing.model.spec.GetShortestRouteWithTimeSpec;

public interface RouteService {
  ShortestRouteResult getShortestRoute(GetShortestRouteSpec spec);

  ShortestRouteWithTimeResult getShortestRouteWithTime(GetShortestRouteWithTimeSpec spec);
}
