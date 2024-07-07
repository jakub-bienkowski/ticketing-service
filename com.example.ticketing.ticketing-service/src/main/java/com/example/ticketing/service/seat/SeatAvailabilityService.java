package com.example.ticketing.service.seat;

import com.example.ticketing.model.booking.Booking;
import com.example.ticketing.model.booking.Passenger;
import com.example.ticketing.model.booking.Ticket;
import com.example.ticketing.model.inventory.Seat;
import com.example.ticketing.model.web.BookingRequest;
import com.example.ticketing.service.search.SearchReservationService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
public class SeatAvailabilityService {

    private final SearchReservationService searchReservationService;

    public SeatAvailabilityService(SearchReservationService searchReservationService) {
        this.searchReservationService = searchReservationService;
    }

    public boolean areSeatsAvailable(BookingRequest request) {
        final List<Seat> requestedSeats = request.getPassengers().stream()
                .flatMap(passenger -> passenger.getRequestedSeats().stream())
                .toList();
        return this.checkAvailability(request.getServiceId(), request.getOriginCode(), request.getDestinationCode(), requestedSeats);
    }

    private boolean checkAvailability(String serviceId, String origin, String destination, List<Seat> requestedSeats) {
        Set<Seat> availableSeats = new HashSet<>(requestedSeats);
        List<Booking> bookings = searchReservationService.getAllServiceBookings(serviceId);

        for (Booking booking : bookings) {
            for (Passenger passenger : booking.getPassengers()) {
                for (Ticket ticket : passenger.getTickets()) {
                    if (requestedSeats.contains(ticket.getSeat()) && !isSeatAvailable(ticket, origin, destination)) {
                        availableSeats.remove(ticket.getSeat());
                    }
                }
            }
        }

        return availableSeats.size() == requestedSeats.size();
    }

    private boolean isSeatAvailable(Ticket ticket, String origin, String destination) {
        return ticket.getOrigin().code().compareTo(origin) > 0 ||
                ticket.getDestination().code().compareTo(destination) < 0;
    }

}