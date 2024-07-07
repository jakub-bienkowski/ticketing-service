package com.example.ticketing.model.web;

import com.example.ticketing.web.Response;

public record ConductorResponse(int statusCode, String body) implements Response {
}
