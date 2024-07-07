package com.example.ticketing.service.seat;

import com.example.ticketing.model.booking.Booking;
import com.example.ticketing.model.booking.Passenger;
import com.example.ticketing.model.booking.Ticket;
import com.example.ticketing.model.inventory.Seat;
import com.example.ticketing.model.inventory.SeatClass;
import com.example.ticketing.model.inventory.Station;
import com.example.ticketing.model.web.BookingRequest;
import com.example.ticketing.service.search.SearchReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class SeatAvailabilityServiceTest {

    private SearchReservationService searchReservationService;
    private SeatAvailabilityService seatAvailabilityService;

    @BeforeEach
    void setUp() {
        searchReservationService = Mockito.mock(SearchReservationService.class);
        seatAvailabilityService = new SeatAvailabilityService(searchReservationService);
    }

    @Test
    void areSeatsAvailable_AllAvailable() {
        List<Seat> requestedSeats = List.of(new Seat("A1", SeatClass.FIRST_CLASS));
        BookingRequest request = new BookingRequest("serviceId", null, "ORIGIN", "DEST", List.of(new BookingRequest.Passenger("P1", requestedSeats)));

        when(searchReservationService.getAllServiceBookings("serviceId")).thenReturn(List.of());

        assertTrue(seatAvailabilityService.areSeatsAvailable(request));
    }

    @Test
    void areSeatsAvailable_SomeNotAvailable() {
        Seat seat1 = new Seat("A1", SeatClass.FIRST_CLASS);
        Seat seat2 = new Seat("A2", SeatClass.FIRST_CLASS);
        List<Seat> requestedSeats = List.of(seat1, seat2);
        BookingRequest request = new BookingRequest("serviceId", null, "ORIGIN", "DEST", List.of(new BookingRequest.Passenger("P1", requestedSeats)));

        Booking booking = new Booking();
        Ticket ticket = new Ticket.Builder()
                .origin(new Station("ORIGIN", ""))
                .destination(new Station("DEST", ""))
                .seat(seat1)
                .build();

        Passenger passenger = new Passenger("P1", List.of(ticket));

        booking.setPassengers(List.of(passenger));

        when(searchReservationService.getAllServiceBookings("serviceId")).thenReturn(List.of(booking));

        assertFalse(seatAvailabilityService.areSeatsAvailable(request));
    }

    @Test
    void areSeatsAvailable_NoneAvailable() {
        Seat seat1 = new Seat("A1", SeatClass.FIRST_CLASS);
        List<Seat> requestedSeats = List.of(seat1);
        BookingRequest request = new BookingRequest("serviceId", null, "ORIGIN", "DEST", List.of(new BookingRequest.Passenger("P1", requestedSeats)));

        Booking booking = new Booking();
        Ticket ticket = new Ticket.Builder()
                .seat(seat1)
                .origin(new Station("ORIGIN", ""))
                .destination(new Station("DEST", ""))
                .build();

        Passenger passenger = new Passenger("P1", List.of(ticket));
        booking.setPassengers(List.of(passenger));
        when(searchReservationService.getAllServiceBookings("serviceId")).thenReturn(List.of(booking));

        assertFalse(seatAvailabilityService.areSeatsAvailable(request));
    }
}