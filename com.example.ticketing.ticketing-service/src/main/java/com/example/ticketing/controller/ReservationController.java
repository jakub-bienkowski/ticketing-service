package com.example.ticketing.controller;

import com.example.ticketing.model.web.BookingRequest;
import com.example.ticketing.model.web.BookingResponse;
import com.example.ticketing.service.reservation.ReservationService;
import com.example.ticketing.web.HttpClient;
import com.example.ticketing.web.Response;

public class ReservationController implements HttpClient {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @Override
    public Response post(String url, BookingRequest body) {
        return reservationService.reserveSeats(body);
    }

    @Override
    public Response get(String url) {
        return new BookingResponse(404, "NOT FOUND.");
    }

}
