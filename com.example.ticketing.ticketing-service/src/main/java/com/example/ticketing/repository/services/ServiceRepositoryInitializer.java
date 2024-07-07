package com.example.ticketing.repository.services;

import com.example.ticketing.model.inventory.Service;
import com.example.ticketing.repository.ModelRepositoryInitializer;
import com.example.ticketing.repository.carriages.CarriageRepository;
import com.example.ticketing.repository.routes.RoutesRepository;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

public class ServiceRepositoryInitializer implements ModelRepositoryInitializer<ServiceRepository> {

    private final ServiceRepository serviceRepository;
    private final RoutesRepository routesRepository;
    private final CarriageRepository carriageRepository;

    public ServiceRepositoryInitializer(ServiceRepository serviceRepository, RoutesRepository routesRepository, CarriageRepository carriageRepository) {
        this.serviceRepository = serviceRepository;
        this.routesRepository = routesRepository;
        this.carriageRepository = carriageRepository;

        this.initialize();
    }

    @Override
    public void initialize() {
        final Service serviceFromLondonToParis = this.buildServiceFromLondonToParis();
        final Service serviceFromParisToAmsterdam = this.buildServiceParisToAmsterdam();
        final Service serviceFromAmsterdamToBerlin = this.buildServiceAmsterdamToBerlin();

        final List<Service> services = List.of(
                serviceFromLondonToParis,
                serviceFromParisToAmsterdam,
                serviceFromAmsterdamToBerlin
        );

        this.serviceRepository.fillRepository(services);

        System.out.println("Service repository initialized with " + services.size() + " services");
    }

    private Service buildServiceFromLondonToParis() {
        return new Service.Builder()
                .id("5159")
                .route(this.routesRepository.get("LondonToParis"))
                .carriages(List.of(
                        this.carriageRepository.get("H"),
                        this.carriageRepository.get("N"),
                        this.carriageRepository.get("T")
                ))
                .departureTime(LocalDateTime.of(2021, Month.APRIL, 1, 8, 0))
            .build();
    }

    private Service buildServiceParisToAmsterdam() {
        return new Service.Builder()
                .id("5160")
                .route(this.routesRepository.get("ParisToAmsterdam"))
                .carriages(List.of(
                        this.carriageRepository.get("A"),
                        this.carriageRepository.get("H"),
                        this.carriageRepository.get("N"),
                        this.carriageRepository.get("T")
                ))
                .departureTime(LocalDateTime.of(2021, Month.APRIL, 1, 18, 15))
                .build();
    }

    private Service buildServiceAmsterdamToBerlin() {
        return new Service.Builder()
                .id("5161")
                .route(this.routesRepository.get("AmsterdamToBerlin"))
                .carriages(List.of(
                        this.carriageRepository.get("N"),
                        this.carriageRepository.get("T")
                ))
                .departureTime(LocalDateTime.of(2021, Month.APRIL, 2, 12, 30))
                .build();
    }




}
