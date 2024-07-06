package com.example.ticketing.repository.routes;

import com.example.ticketing.model.inventory.Route;
import com.example.ticketing.model.inventory.Stop;
import com.example.ticketing.repository.RepositoryInitializer;
import com.example.ticketing.repository.stations.StationRepository;

import java.util.List;

public class RoutesRepositoryInitializer implements RepositoryInitializer<RoutesRepository> {

    private final RoutesRepository routesRepository;
    private final StationRepository stationRepository;

    public RoutesRepositoryInitializer(RoutesRepository routesRepository, StationRepository stationRepository) {
        this.routesRepository = routesRepository;
        this.stationRepository = stationRepository;
    }

    @Override
    public void initialize() {
        final Route parisToLondon = createRouteLondonToParis();
        final Route parisToAmsterdam = createRouteParisToAmsterdam();
        final Route amsterdamToBerlin = createRouteAmsterdamToBerlin();

        final List<Route> routes = List.of(parisToLondon, parisToAmsterdam, amsterdamToBerlin);
        routesRepository.fillRepository(routes);

        System.out.println("Routes repository initialized with " + routes.size() + " routes");
    }

    private Route createRouteLondonToParis() {
        return new Route.Builder()
                .id("LondonToParis")
                .stops(List.of(
                        new Stop(stationRepository.get("LON"), 0),
                        new Stop(stationRepository.get("CAL"), 200.25),
                        new Stop(stationRepository.get("AMI"), 112.75),
                        new Stop(stationRepository.get("PAR"), 250.5)
                ))
                .build();
    }

    private Route createRouteParisToAmsterdam() {
        return new Route.Builder()
                .id("ParisToAmsterdam")
                .stops(List.of(
                        new Stop(stationRepository.get("PAR"), 0),
                        new Stop(stationRepository.get("BRU"), 66.02),
                        new Stop(stationRepository.get("ROT"), 124.45),
                        new Stop(stationRepository.get("AMS"), 173.5)
                ))
                .build();
    }

    private Route createRouteAmsterdamToBerlin() {
        return new Route.Builder()
                .id("AmsterdamToBerlin")
                .stops(List.of(
                        new Stop(stationRepository.get("AMS"), 0),
                        new Stop(stationRepository.get("MUN"), 211.10),
                        new Stop(stationRepository.get("HAN"), 85.00),
                        new Stop(stationRepository.get("BER"), 233.63)
                ))
                .build();
    }

}
