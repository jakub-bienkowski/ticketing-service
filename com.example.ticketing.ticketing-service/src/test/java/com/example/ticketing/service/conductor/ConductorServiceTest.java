package com.example.ticketing.service.conductor;

import com.example.ticketing.exceptions.SeatNotExists;
import com.example.ticketing.model.booking.Passenger;
import com.example.ticketing.model.web.ConductorResponse;
import com.example.ticketing.service.search.SearchReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ConductorServiceTest {

    private SearchReservationService searchReservationService;
    private ConductorService conductorService;

    @BeforeEach
    void setUp() {
        searchReservationService = Mockito.mock(SearchReservationService.class);
        conductorService = new ConductorService(searchReservationService);
    }

    @Test
    void getData_PassengersBoarding() {
        String url = "/passengers/boarding/stationCode";
        when(searchReservationService.getNumberOfPassengersBoarding("stationCode")).thenReturn(10L);

        ConductorResponse response = conductorService.getData(url);

        assertEquals(200, response.statusCode());
        assertEquals("Number of passengers boarding at stationCode is 10", response.body());
    }

    @Test
    void getData_PassengersLeaving() {
        String url = "/passengers/leaving/stationCode";
        when(searchReservationService.getNumberOfPassengersLeaving("stationCode")).thenReturn(5L);

        ConductorResponse response = conductorService.getData(url);

        assertEquals(200, response.statusCode());
        assertEquals("Number of passengers leaving at stationCode is 5", response.body());
    }

    @Test
    void getData_PassengersBetween() {
        String url = "/passengers/between?origin=originCode&dest=destinationCode";
        when(searchReservationService.getAllPassengersBetween("originCode", "destinationCode")).thenReturn(15L);

        ConductorResponse response = conductorService.getData(url);

        assertEquals(200, response.statusCode());
        assertEquals("Number of passengers between originCode and destinationCode is 15", response.body());
    }

    @Test
    void getData_GetPassenger() throws SeatNotExists {
        String url = "/passengers/get?location=stationCode&serviceId=serviceId&date=2023-01-01&seat=A1";
        Passenger mockPassenger = new Passenger("John Doe", null);
        when(searchReservationService.getPassenger("serviceId", "2023-01-01", "A1", "stationCode")).thenReturn(mockPassenger);

        ConductorResponse response = conductorService.getData(url);
        assertEquals(200, response.statusCode());
    }
}