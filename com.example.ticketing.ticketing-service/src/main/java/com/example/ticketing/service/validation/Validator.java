package com.example.ticketing.service.validation;

import com.example.ticketing.exceptions.BookingValidationException;

public interface Validator<T> {

    void validate(T item) throws BookingValidationException;
}
