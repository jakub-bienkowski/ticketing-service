package com.example.ticketing.model.web;

import com.example.ticketing.model.inventory.Seat;

import java.time.LocalDateTime;
import java.util.List;

public class BookingRequest {

    private final String serviceId;
    private final LocalDateTime serviceDepartureTime;
    private final String originCode;
    private final String destinationCode;
    private final List<Passenger> passengers;

    public BookingRequest(String serviceId, LocalDateTime serviceDepartureTime, String originCode, String destinationCode, List<Passenger> passengers) {
        this.serviceId = serviceId;
        this.serviceDepartureTime = serviceDepartureTime;
        this.originCode = originCode;
        this.destinationCode = destinationCode;
        this.passengers = passengers;
    }

    public String getServiceId() {
        return serviceId;
    }

    public LocalDateTime getServiceDepartureTime() {
        return serviceDepartureTime;
    }

    public String getOriginCode() {
        return originCode;
    }

    public String getDestinationCode() {
        return destinationCode;
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public static class Passenger {
        private final String name;
        private final List<Seat> requestedSeats;

        public Passenger(String name, List<Seat> requestedSeats) {
            this.name = name;
            this.requestedSeats = requestedSeats;
        }

        public String getName() {
            return name;
        }

        public List<Seat> getRequestedSeats() {
            return requestedSeats;
        }
    }
}
