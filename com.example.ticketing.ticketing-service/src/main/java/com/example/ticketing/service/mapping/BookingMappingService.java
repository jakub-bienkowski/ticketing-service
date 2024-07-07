package com.example.ticketing.service.mapping;

import com.example.ticketing.model.booking.Booking;
import com.example.ticketing.model.booking.Passenger;
import com.example.ticketing.model.booking.Ticket;
import com.example.ticketing.model.web.BookingRequest;
import com.example.ticketing.repository.services.ServiceRepository;
import com.example.ticketing.repository.stations.StationRepository;

import java.util.List;

public class BookingMappingService {

    private final ServiceRepository serviceRepository;
    private final StationRepository stationRepository;

    public BookingMappingService(ServiceRepository serviceRepository, StationRepository stationRepository) {
        this.serviceRepository = serviceRepository;
        this.stationRepository = stationRepository;
    }

    public Booking mapBookingRequest(BookingRequest request) {
        final List<Passenger> passengers = mapPassengers(request);
        final Booking booking = new Booking();
        booking.setPassengers(passengers);
        return booking;
    }

    private List<Passenger> mapPassengers(BookingRequest request) {
        return request.getPassengers().stream()
                .map(passengerDetail -> {
                    final List<Ticket> tickets = getTickets(request, passengerDetail);
                    return new Passenger(passengerDetail.getName(), tickets);
                })
                .toList();
    }

    private List<Ticket> getTickets(BookingRequest request, BookingRequest.Passenger passengerDetail) {
        return passengerDetail.getRequestedSeats().stream()
                .map(seat -> new Ticket.Builder()
                        .seat(seat)
                        .origin(stationRepository.get(request.getOriginCode()))
                        .destination(stationRepository.get(request.getDestinationCode()))
                        .service(serviceRepository.get(request.getServiceId()))
                        .build())
                .toList();
    }

}
