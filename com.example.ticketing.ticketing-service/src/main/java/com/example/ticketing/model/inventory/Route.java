package com.example.ticketing.model.inventory;

import java.util.ArrayList;
import java.util.List;

public class Route {
    private final String id;
    private final List<Stop> stops;

    private Route(Builder builder) {
        this.id = builder.id;
        this.stops = builder.stops;
    }

    public String getId() {
        return id;
    }

    public List<Stop> getStops() {
        return stops;
    }

    public static class Builder {
        private String id;
        private List<Stop> stops = new ArrayList<>();

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder stop(Stop stop) {
            this.stops.add(stop);
            return this;
        }

        public Builder stops(List<Stop> stops) {
            this.stops = stops;
            return this;
        }

        public Route build() {
            return new Route(this);
        }
    }
}