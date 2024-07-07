package com.example.ticketing.exceptions;

public class InvalidDateException extends BookingValidationException {

    public InvalidDateException(String description) {
        super(description);
    }

}
