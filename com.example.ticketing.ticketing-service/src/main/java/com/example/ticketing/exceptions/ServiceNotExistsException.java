package com.example.ticketing.exceptions;

public class ServiceNotExistsException extends BookingValidationException {

    public ServiceNotExistsException(String errorCode) {
        super(errorCode);
    }
}
