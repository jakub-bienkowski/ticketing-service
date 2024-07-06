package com.example.ticketing.model.booking;

import java.util.ArrayList;
import java.util.List;

public class Passenger {

    private final String name;
    private final List<Ticket> tickets;

    private Passenger(Builder builder) {
        this.name = builder.name;
        this.tickets = builder.tickets;
    }

    public String getName() {
        return name;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public static class Builder {
        private String name;
        private List<Ticket> tickets = new ArrayList<>();

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder ticket(Ticket ticket) {
            if (this.tickets == null) {
                this.tickets = new ArrayList<>();
            }
            this.tickets.add(ticket);
            return this;
        }

        public Builder tickets(List<Ticket> tickets) {
            this.tickets = tickets;
            return this;
        }

        public Passenger build() {
            return new Passenger(this);
        }
    }

}