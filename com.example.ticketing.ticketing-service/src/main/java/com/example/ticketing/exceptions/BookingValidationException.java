package com.example.ticketing.exceptions;

public abstract class BookingValidationException extends Exception {

    protected String description;

    public BookingValidationException(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
