package com.example.ticketing.web;

import com.example.ticketing.model.web.BookingRequest;

public interface HttpClient {

    Response post(String url, BookingRequest body);
    Response get(String url);

}
