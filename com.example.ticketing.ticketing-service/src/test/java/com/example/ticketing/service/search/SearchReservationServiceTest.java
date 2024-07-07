package com.example.ticketing.service.search;

import com.example.ticketing.exceptions.SeatNotExists;
import com.example.ticketing.model.booking.Booking;
import com.example.ticketing.model.booking.Passenger;
import com.example.ticketing.model.inventory.Service;
import com.example.ticketing.repository.reservations.ReservationRepository;
import com.example.ticketing.repository.services.ServiceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class SearchReservationServiceTest {

    private ReservationRepository bookingRepository;
    private ServiceRepository serviceRepository;
    private SearchReservationService searchService;

    @BeforeEach
    void setUp() {
        bookingRepository = Mockito.mock(ReservationRepository.class);
        serviceRepository = Mockito.mock(ServiceRepository.class);
        searchService = new SearchReservationService(bookingRepository, serviceRepository);
    }

    @Test
    void getAllServiceBookings_ReturnsCorrectBookings() {
        Booking booking1 = new Booking();
        Booking booking2 = new Booking();
        when(bookingRepository.getAll()).thenReturn(Arrays.asList(booking1, booking2));
        List<Booking> bookings = searchService.getAllServiceBookings("serviceId");
        assertEquals(0, bookings.size());
    }

    @Test
    void getNumberOfPassengersBoarding_ReturnsCorrectCount() {
        when(bookingRepository.getAll()).thenReturn(List.of());
        long count = searchService.getNumberOfPassengersBoarding("locationCode");
        assertEquals(0, count);
    }

    @Test
    void getNumberOfPassengersLeaving_ReturnsCorrectCount() {
        when(bookingRepository.getAll()).thenReturn(List.of());
        long count = searchService.getNumberOfPassengersLeaving("locationCode");
        assertEquals(0, count);
    }

    @Test
    void getAllPassengersBetween_ReturnsCorrectCount() {
        when(bookingRepository.getAll()).thenReturn(List.of());
        when(serviceRepository.get(Mockito.anyString())).thenReturn(new Service.Builder().build());
        long count = searchService.getAllPassengersBetween("originCode", "destinationCode");
        assertEquals(0, count);
    }
}