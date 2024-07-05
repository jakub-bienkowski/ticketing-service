package com.example.ticketing.repository.routes;

import com.example.ticketing.model.inventory.Route;
import com.example.ticketing.repository.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoutesRepository implements Repository<Route, String> {

    private final Map<String, Route> stations = new HashMap<>();

    @Override
    public void fillRepository(List<Route> items) {
        items.forEach(route -> stations.put(route.getId(), route));
    }

    @Override
    public Route get(String id) {
        return stations.get(id);
    }

}