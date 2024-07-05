package com.example.ticketing.model.booking;

public class Passenger {
    private final String id;
    private final String name;

    private Passenger(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static class Builder {
        private String id;
        private String name;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Passenger build() {
            return new Passenger(this);
        }
    }
}