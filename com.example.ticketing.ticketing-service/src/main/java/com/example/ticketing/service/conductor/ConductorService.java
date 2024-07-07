package com.example.ticketing.service.conductor;

import com.example.ticketing.exceptions.SeatNotExists;
import com.example.ticketing.model.booking.Passenger;
import com.example.ticketing.model.web.ConductorResponse;
import com.example.ticketing.service.search.SearchReservationService;

public class ConductorService {

    private final SearchReservationService searchReservationService;

    public ConductorService(SearchReservationService searchReservationService) {
        this.searchReservationService = searchReservationService;
    }

    public ConductorResponse getData(String url) {
        try {
            return parseUrl(url);
        } catch (SeatNotExists e) {
            return new ConductorResponse(404, "SEAT NOT FOUND");
        } catch (Exception e) {
            return new ConductorResponse(500, "INTERNAL SERVER ERROR");
        }
    }

    private ConductorResponse parseUrl(String url) throws SeatNotExists {
        if (url.startsWith("/passengers/boarding")) {
            final String stationCode = url.substring(url.lastIndexOf("/") + 1);
            final long numberOfPassengers = searchReservationService.getNumberOfPassengersBoarding(stationCode);
            return new ConductorResponse(200, "Number of passengers boarding at " + stationCode + " is " + numberOfPassengers);
        }
        if (url.startsWith("/passengers/leaving")) {
            final String stationCode = url.substring(url.lastIndexOf("/") + 1);
            final long numberOfPassengers = searchReservationService.getNumberOfPassengersLeaving(stationCode);
            return new ConductorResponse(200, "Number of passengers leaving at " + stationCode + " is " + numberOfPassengers);
        }
        if (url.startsWith("/passengers/between")) {
            final String originCode = url.substring(url.indexOf("origin=") + 7, url.indexOf("&dest"));
            final String destinationCode = url.substring(url.lastIndexOf("=") + 1);

            final long numberOfPassengers = searchReservationService.getAllPassengersBetween(originCode, destinationCode);
            return new ConductorResponse(200, "Number of passengers between " + originCode + " and " + destinationCode + " is " + numberOfPassengers);
        }
        if (url.startsWith("/passengers/get")) {
            final String serviceId = url.substring(url.indexOf("serviceId=") + 10, url.indexOf("&date"));
            final String date = url.substring(url.indexOf("date=") + 5, url.indexOf("&seat"));
            final String seat = url.substring(url.lastIndexOf("=") + 1);
            final String location = url.substring(url.indexOf("location=") + 9, url.indexOf("&serviceId"));

            final Passenger passenger = searchReservationService.getPassenger(serviceId, date, seat, location);
            return new ConductorResponse(200, "Passenger with service ID " + serviceId + " and seat " + seat + " is " + passenger.getName());
        } else {
            return new ConductorResponse(400, "BAD REQUEST");
        }
    }

}
