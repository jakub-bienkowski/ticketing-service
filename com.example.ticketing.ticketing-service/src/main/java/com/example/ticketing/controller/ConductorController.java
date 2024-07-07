package com.example.ticketing.controller;

import com.example.ticketing.model.web.BookingRequest;
import com.example.ticketing.model.web.ConductorResponse;
import com.example.ticketing.service.conductor.ConductorService;
import com.example.ticketing.web.HttpClient;
import com.example.ticketing.web.Response;

public class ConductorController implements HttpClient {

    private final ConductorService conductorService;

    public ConductorController(ConductorService conductorService) {
        this.conductorService = conductorService;
    }

    @Override
    public Response get(String url) {
        return this.conductorService.getData(url);
    }

    @Override
    public Response post(String url, BookingRequest body) {
        return new ConductorResponse(405, "METHOD NOT ALLOWED");
    }


}
