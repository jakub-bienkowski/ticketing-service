package com.example.ticketing.model.inventory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Service {

    private final String id;
    private final Route route;
    private final LocalDateTime departureTime;
    private final List<Carriage> carriages;

    private Service(Builder builder) {
        this.id = builder.id;
        this.route = builder.route;
        this.departureTime = builder.departureTime;
        this.carriages = builder.carriages;
    }

    public String getId() {
        return id;
    }

    public Route getRoute() {
        return route;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public List<Carriage> getCarriages() {
        return carriages;
    }

    public static class Builder {
        private String id;
        private Route route;
        private LocalDateTime departureTime;
        private List<Carriage> carriages = new ArrayList<>();

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder route(Route route) {
            this.route = route;
            return this;
        }

        public Builder departureTime(LocalDateTime departureTime) {
            this.departureTime = departureTime;
            return this;
        }

        public Builder carriages(List<Carriage> carriages) {
            this.carriages = carriages;
            return this;
        }

        public Service build() {
            return new Service(this);
        }
    }
}