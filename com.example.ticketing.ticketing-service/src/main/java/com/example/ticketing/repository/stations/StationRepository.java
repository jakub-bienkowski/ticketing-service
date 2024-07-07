package com.example.ticketing.repository.stations;

import com.example.ticketing.model.inventory.Station;
import com.example.ticketing.repository.ModelRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StationRepository implements ModelRepository<Station, String> {

    private final Map<String, Station> stations = new HashMap<>();

    @Override
    public void fillRepository(List<Station> items) {
        items.forEach(station -> stations.put(station.code(), station));
    }

    @Override
    public Station get(String id) {
        return stations.get(id);
    }
}