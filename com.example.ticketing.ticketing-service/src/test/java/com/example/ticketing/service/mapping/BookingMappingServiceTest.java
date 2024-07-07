package com.example.ticketing.service.mapping;

import com.example.ticketing.model.booking.Booking;
import com.example.ticketing.model.booking.Passenger;
import com.example.ticketing.model.booking.Ticket;
import com.example.ticketing.model.inventory.Seat;
import com.example.ticketing.model.inventory.Service;
import com.example.ticketing.model.inventory.Station;
import com.example.ticketing.model.web.BookingRequest;
import com.example.ticketing.repository.services.ServiceRepository;
import com.example.ticketing.repository.stations.StationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class BookingMappingServiceTest {

    private ServiceRepository serviceRepository;
    private StationRepository stationRepository;
    private BookingMappingService bookingMappingService;

    @BeforeEach
    void setUp() {
        serviceRepository = Mockito.mock(ServiceRepository.class);
        stationRepository = Mockito.mock(StationRepository.class);
        bookingMappingService = new BookingMappingService(serviceRepository, stationRepository);
    }

    @Test
    void mapBookingRequest_SinglePassengerSingleSeat() {
        Service mockService = new Service.Builder().id("serviceId").build();
        Station originStation = new Station("originCode", "Origin Station");
        Station destinationStation = new Station("destinationCode", "Destination Station");
        LocalDateTime departureTime = LocalDateTime.now();

        when(serviceRepository.get("serviceId")).thenReturn(mockService);
        when(stationRepository.get("originCode")).thenReturn(originStation);
        when(stationRepository.get("destinationCode")).thenReturn(destinationStation);

        BookingRequest request = new BookingRequest("serviceId", departureTime, "originCode", "destinationCode", List.of(new BookingRequest.Passenger("Passenger 1", List.of(new Seat("A1", null)))));

        Booking result = bookingMappingService.mapBookingRequest(request);

        assertEquals(1, result.getPassengers().size());
        Passenger passenger = result.getPassengers().get(0);
        assertEquals("Passenger 1", passenger.getName());
        assertEquals(1, passenger.getTickets().size());
        Ticket ticket = passenger.getTickets().get(0);
        assertEquals("A1", ticket.getSeat().id());
        assertEquals(originStation, ticket.getOrigin());
        assertEquals(destinationStation, ticket.getDestination());
        assertEquals(mockService, ticket.getService());
    }

}