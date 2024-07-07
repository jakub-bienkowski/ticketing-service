package com.example.ticketing.repository.stations;

import com.example.ticketing.model.inventory.Station;
import com.example.ticketing.repository.ModelRepositoryInitializer;

import java.util.List;

public class StationRepositoryInitializer implements ModelRepositoryInitializer<StationRepository> {

    final StationRepository repository;

    public StationRepositoryInitializer(StationRepository repository) {
        this.repository = repository;

        this.initialize();
    }

    @Override
    public void initialize() {
        final List<Station> stations = this.buildStationList();
        this.repository.fillRepository(stations);

        System.out.println("Station repository initialized with " + stations.size() + " stations");

    }

    private List<Station> buildStationList() {
        return List.of(
                new Station("AMI", "Amiens"),
                new Station("AMS", "Amsterdam"),
                new Station("BER", "Berlin"),
                new Station("BRU", "Brüssel"),
                new Station("CAL", "Calais"),
                new Station("HAN", "Hannover"),
                new Station("LON", "London"),
                new Station("MUN", "Münster"),
                new Station("PAR", "Paris"),
                new Station("ROT", "Rotterdam")
        );
    }

}
