package com.example.ticketing.model.booking;

import java.util.ArrayList;
import java.util.List;

public class Booking {
    private final String id;
    private final List<Ticket> tickets;

    private Booking(Builder builder) {
        this.id = builder.id;
        this.tickets = builder.tickets;
    }

    public String getId() {
        return id;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public static class Builder {
        private String id;
        private List<Ticket> tickets = new ArrayList<>();

        public Builder id(String id) {
            this.id = id;
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

        public Booking build() {
            return new Booking(this);
        }
    }
}