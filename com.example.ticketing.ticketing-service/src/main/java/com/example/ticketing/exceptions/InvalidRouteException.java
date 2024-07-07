package com.example.ticketing.exceptions;

public class InvalidRouteException extends BookingValidationException {

    public InvalidRouteException(String description) {
        super(description);
    }

}
