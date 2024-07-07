package com.example.ticketing.model.booking;

import com.example.ticketing.model.inventory.Seat;
import com.example.ticketing.model.inventory.Service;
import com.example.ticketing.model.inventory.Station;

public class Ticket {

    private final Seat seat;
    private final Station origin;
    private final Station destination;
    private final Service service;

    private Ticket(Builder builder) {
        this.seat = builder.seat;
        this.origin = builder.origin;
        this.destination = builder.destination;
        this.service = builder.service;
    }

    public Seat getSeat() {
        return seat;
    }

    public Station getOrigin() {
        return origin;
    }

    public Station getDestination() {
        return destination;
    }

    public Service getService() {
        return service;
    }

    public static class Builder {
        private Seat seat;
        private Station origin;
        private Station destination;
        private Service service;

        public Builder seat(Seat seat) {
            this.seat = seat;
            return this;
        }

        public Builder origin(Station origin) {
            this.origin = origin;
            return this;
        }

        public Builder destination(Station destination) {
            this.destination = destination;
            return this;
        }

        public Builder service(Service service) {
            this.service = service;
            return this;
        }

        public Ticket build() {
            return new Ticket(this);
        }
    }

}