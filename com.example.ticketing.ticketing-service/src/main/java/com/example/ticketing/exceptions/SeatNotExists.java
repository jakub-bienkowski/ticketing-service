package com.example.ticketing.exceptions;

public class SeatNotExists extends BookingValidationException {

    public SeatNotExists(String description) {
        super(description);
    }
}
