package com.example.ticketing.model.web;

import com.example.ticketing.web.Response;

public class BookingResponse implements Response {

    @Override
    public int getStatusCode() {
        return 0;
    }

    @Override
    public String getBody() {
        return "";
    }

}
