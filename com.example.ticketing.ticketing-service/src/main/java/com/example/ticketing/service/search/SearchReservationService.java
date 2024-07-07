package com.example.ticketing.service.search;

import com.example.ticketing.model.booking.Booking;
import com.example.ticketing.repository.reservations.ReservationRepository;

import java.util.List;
import java.util.stream.Collectors;

public class SearchReservationService {

    private final ReservationRepository bookingRepository;

    public SearchReservationService(ReservationRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public List<Booking> getAllServiceBookings(String serviceId) {
        return bookingRepository.getAll().stream()
                .filter(booking -> booking.getPassengers().stream()
                        .flatMap(passenger -> passenger.getTickets().stream())
                        .anyMatch(ticket -> ticket.getService().getId().equals(serviceId)))
                .collect(Collectors.toList());
    }
}
