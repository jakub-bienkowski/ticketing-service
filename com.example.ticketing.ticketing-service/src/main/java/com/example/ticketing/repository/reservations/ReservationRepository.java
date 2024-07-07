package com.example.ticketing.repository.reservations;

import com.example.ticketing.model.booking.Booking;
import com.example.ticketing.repository.CrudRepository;
import com.example.ticketing.utils.IdGenerator;

import java.util.HashMap;
import java.util.List;

public class ReservationRepository implements CrudRepository<Booking, String> {

    private final HashMap<String, Booking> reservation = new HashMap<>();

    @Override
    public Booking get(String id) {
        return this.reservation.get(id);
    }

    @Override
    public List<Booking> getAll() {
        return this.reservation.values().stream().toList();
    }

    @Override
    public Booking add(Booking item) {
        item.setId(IdGenerator.generateId());
        this.reservation.put(item.getId(), item);
        return item;
    }

    @Override
    public void remove(Booking item) {
        this.reservation.remove(item.getId());
    }

}
