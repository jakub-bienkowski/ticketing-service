package com.example.ticketing.model.web;

import com.example.ticketing.web.Response;

public record BookingResponse(int statusCode, String body) implements Response {

}
