package com.example.ticketing.model.booking;

import java.util.ArrayList;
import java.util.List;

public class Passenger {

    private final String name;
    private final List<Ticket> tickets;

    public Passenger(String name, List<Ticket> tickets) {
        this.name = name;
        this.tickets = tickets;
    }

    public String getName() {
        return name;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }


}