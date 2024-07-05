package com.example.ticketing.model.booking;

import com.example.ticketing.model.inventory.Seat;

public class Ticket {
    private final String id;
    private final Seat seat;
    private final Passenger passenger;

    private Ticket(Builder builder) {
        this.id = builder.id;
        this.seat = builder.seat;
        this.passenger = builder.passenger;
    }

    public String getId() {
        return id;
    }

    public Seat getSeat() {
        return seat;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public static class Builder {
        private String id;
        private Seat seat;
        private Passenger passenger;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder seat(Seat seat) {
            this.seat = seat;
            return this;
        }

        public Builder passenger(Passenger passenger) {
            this.passenger = passenger;
            return this;
        }

        public Ticket build() {
            return new Ticket(this);
        }
    }
}