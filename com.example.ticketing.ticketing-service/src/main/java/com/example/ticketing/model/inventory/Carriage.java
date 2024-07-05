package com.example.ticketing.model.inventory;

import java.util.List;

public record Carriage(String id, List<Seat> seats) {
}