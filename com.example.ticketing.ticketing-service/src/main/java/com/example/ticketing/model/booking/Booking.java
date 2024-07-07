package com.example.ticketing.model.booking;

import java.util.ArrayList;
import java.util.List;

public class Booking {

    private String id;
    private List<Passenger> passengers;


    public Booking() {
        this.passengers = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<Passenger> passengers) {
        this.passengers = passengers;
    }
}