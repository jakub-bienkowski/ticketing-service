package com.example.ticketing.model.booking;

import java.util.ArrayList;
import java.util.List;

public class Booking {

    private final String id;
    private final List<Passenger> passengers;

    private Booking(Builder builder) {
        this.id = builder.id;
        this.passengers = builder.passengers;
    }

    public String getId() {
        return id;
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public static class Builder {
        private String id;
        private List<Passenger> passengers = new ArrayList<>();

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder passenger(Passenger passenger) {
            if (this.passengers == null) {
                this.passengers = new ArrayList<>();
            }
            this.passengers.add(passenger);
            return this;
        }

        public Builder passengers(List<Passenger> passengers) {
            this.passengers = passengers;
            return this;
        }

        public Booking build() {
            return new Booking(this);
        }
    }
}