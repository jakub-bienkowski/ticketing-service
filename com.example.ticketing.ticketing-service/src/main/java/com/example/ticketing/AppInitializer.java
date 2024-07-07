package com.example.ticketing;

import com.example.ticketing.controller.ConductorController;
import com.example.ticketing.controller.ReservationController;
import com.example.ticketing.repository.carriages.CarriageRepository;
import com.example.ticketing.repository.carriages.CarriageRepositoryInitializer;
import com.example.ticketing.repository.reservations.ReservationRepository;
import com.example.ticketing.repository.routes.RoutesRepository;
import com.example.ticketing.repository.routes.RoutesRepositoryInitializer;
import com.example.ticketing.repository.services.ServiceRepository;
import com.example.ticketing.repository.services.ServiceRepositoryInitializer;
import com.example.ticketing.repository.stations.StationRepository;
import com.example.ticketing.repository.stations.StationRepositoryInitializer;
import com.example.ticketing.service.conductor.ConductorService;
import com.example.ticketing.service.mapping.BookingMappingService;
import com.example.ticketing.service.reservation.ReservationService;
import com.example.ticketing.service.search.SearchReservationService;
import com.example.ticketing.service.seat.SeatAvailabilityService;
import com.example.ticketing.service.validation.RequestValidationService;

public class AppInitializer {

    private ReservationController reservationController;
    private ConductorController conductorController;

    public AppInitializer() {
        this.initialize();

        System.out.println("All components have been initialized.");
    }

    private void initialize() {
        final StationRepository stationRepository = new StationRepository();
        new StationRepositoryInitializer(stationRepository);

        final CarriageRepository carriageRepository = new CarriageRepository();
        new CarriageRepositoryInitializer(carriageRepository);

        final RoutesRepository routesRepository = new RoutesRepository();
        new RoutesRepositoryInitializer(routesRepository, stationRepository);

        final ServiceRepository serviceRepository = new ServiceRepository();
        new ServiceRepositoryInitializer(serviceRepository, routesRepository, carriageRepository);

        final ReservationRepository reservationRepository = new ReservationRepository();

        this.initializeServices(serviceRepository, reservationRepository, stationRepository);
    }

    private void initializeServices(ServiceRepository serviceRepository, ReservationRepository reservationRepository, StationRepository stationRepository) {
        final RequestValidationService requestValidationService = new RequestValidationService(serviceRepository);
        final SearchReservationService searchReservationService = new SearchReservationService(reservationRepository, serviceRepository);
        final SeatAvailabilityService seatAvailabilityService = new SeatAvailabilityService(searchReservationService);
        final BookingMappingService bookingMappingService = new BookingMappingService(serviceRepository, stationRepository);
        final ReservationService reservationService = new ReservationService(reservationRepository, requestValidationService, seatAvailabilityService, bookingMappingService);
        final ConductorService conductorService = new ConductorService(searchReservationService);

        this.initializeReservationController(reservationService);
        this.initializeConductorController(conductorService);
    }

    private void initializeReservationController(ReservationService reservationService) {
        this.reservationController = new ReservationController(reservationService);
    }

    private void initializeConductorController(ConductorService conductorService) {
        this.conductorController = new ConductorController(conductorService);
    }

    public ReservationController getReservationController() {
        return reservationController;
    }

    public ConductorController getConductorController() {
        return conductorController;
    }
}