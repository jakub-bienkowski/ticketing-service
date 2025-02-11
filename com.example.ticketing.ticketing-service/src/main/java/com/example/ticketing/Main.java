package com.example.ticketing;

import com.example.ticketing.controller.ConductorController;
import com.example.ticketing.controller.ReservationController;
import com.example.ticketing.model.inventory.Seat;
import com.example.ticketing.model.inventory.SeatClass;
import com.example.ticketing.model.web.BookingRequest;
import com.example.ticketing.web.Response;

import java.time.LocalDateTime;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        final AppInitializer initializer = new AppInitializer();
        final ReservationController reservationController = initializer.getReservationController();

        performScenario1(reservationController);
        performScenario2(reservationController);
        performScenario3(reservationController);
        performScenario4(reservationController);

        final ConductorController conductorController = initializer.getConductorController();
        performConductorQueries(conductorController);
    }

    private static void performScenario1(ReservationController reservationController) {
        BookingRequest request = getRequestForScenario1();
        Response bookingResponse = reservationController.post("", request);

        System.out.println("------------- Scenario 1 --------------");
        System.out.println("Booking Status: " + bookingResponse.statusCode());
        System.out.println("Message: " + bookingResponse.body());
    }

    private static void performScenario2(ReservationController reservationController) {
        BookingRequest request = getRequestForScenario1();
        Response bookingResponse = reservationController.post("", request);

        System.out.println("------------- Scenario 2 --------------");
        System.out.println("Booking Status: " + bookingResponse.statusCode());
        System.out.println("Message: " + bookingResponse.body());
    }

    private static void performScenario3(ReservationController reservationController) {
        List<BookingRequest> requests = getRequestForScenario3();
        Response bookingResponseLondonToParis = reservationController.post("", requests.get(0));
        Response bookingResponseParisToAmsterdam = reservationController.post("", requests.get(1));

        System.out.println("------------- Scenario 3 --------------");
        System.out.println("London to Paris Booking Status: " + bookingResponseLondonToParis.statusCode());
        System.out.println("London to Paris Message: " + bookingResponseLondonToParis.body());
        System.out.println("Paris to Amsterdam Booking Status: " + bookingResponseParisToAmsterdam.statusCode());
        System.out.println("Paris to Amsterdam Message: " + bookingResponseParisToAmsterdam.body());
    }

    private static void performScenario4(ReservationController reservationController) {
        List<BookingRequest> requests = getRequestForScenario3();
        Response bookingResponseLondonToParis = reservationController.post("", requests.get(0));
        Response bookingResponseParisToAmsterdam = reservationController.post("", requests.get(1));

        System.out.println("------------- Scenario 4 --------------");
        System.out.println("London to Paris Booking Status: " + bookingResponseLondonToParis.statusCode());
        System.out.println("London to Paris Message: " + bookingResponseLondonToParis.body());
        System.out.println("Paris to Amsterdam Booking Status: " + bookingResponseParisToAmsterdam.statusCode());
        System.out.println("Paris to Amsterdam Message: " + bookingResponseParisToAmsterdam.body());
    }

    private static BookingRequest getRequestForScenario1() {
        String fromStation = "PAR";
        String toStation = "AMS";
        String serviceId = "5160";
        LocalDateTime date = LocalDateTime.of(2021, 4, 1, 18, 15);
        BookingRequest.Passenger passengerA = new BookingRequest.Passenger("Passenger A", List.of(new Seat("A11", SeatClass.FIRST_CLASS)));
        BookingRequest.Passenger passengerB = new BookingRequest.Passenger("Passenger B", List.of(new Seat("A12", SeatClass.FIRST_CLASS)));


        return new BookingRequest(serviceId, date, fromStation, toStation, List.of(passengerA, passengerB));
    }

    private static List<BookingRequest> getRequestForScenario3() {
        String fromStationLondonToParis = "LON";
        String toStationLondonToParis = "PAR";
        String serviceIdLondonToParis = "5159";
        LocalDateTime dateLondonToParis = LocalDateTime.of(2021, 4, 1, 8, 0);

        String fromStationParisToAmsterdam = "PAR";
        String toStationParisToAmsterdam = "AMS";
        String serviceIdParisToAmsterdam = "5160";
        LocalDateTime dateParisToAmsterdam = LocalDateTime.of(2021, 4, 1, 18, 15);

        // Passenger  London to Paris
        BookingRequest.Passenger passenger1LondonToParis = new BookingRequest.Passenger("Passenger 1", List.of(new Seat("H1", SeatClass.FIRST_CLASS)));
        BookingRequest.Passenger passenger2LondonToParis = new BookingRequest.Passenger("Passenger 2", List.of(new Seat("N5", SeatClass.SECOND_CLASS)));

        // Passenger Paris to Amsterdam
        BookingRequest.Passenger passenger1ParisToAmsterdam = new BookingRequest.Passenger("Passenger 1", List.of(new Seat("A1", SeatClass.FIRST_CLASS)));
        BookingRequest.Passenger passenger2ParisToAmsterdam = new BookingRequest.Passenger("Passenger 2", List.of(new Seat("T7", SeatClass.SECOND_CLASS)));
        // Create booking requests
        BookingRequest bookingRequestLondonToParis = new BookingRequest(serviceIdLondonToParis, dateLondonToParis, fromStationLondonToParis, toStationLondonToParis, List.of(passenger1LondonToParis, passenger2LondonToParis));
        BookingRequest bookingRequestParisToAmsterdam = new BookingRequest(serviceIdParisToAmsterdam, dateParisToAmsterdam, fromStationParisToAmsterdam, toStationParisToAmsterdam, List.of(passenger1ParisToAmsterdam, passenger2ParisToAmsterdam));

        return List.of(bookingRequestLondonToParis, bookingRequestParisToAmsterdam);
    }

    private static void performConductorQueries(ConductorController conductorController) {
        // Query 1: Number of passengers boarding at London
        Response responseBoardingLondon = conductorController.get("/passengers/boarding/LON");
        System.out.println("Query 1: " + responseBoardingLondon.body());

        // Query 2: Number of passengers leaving at Paris
        Response responseLeavingParis = conductorController.get("/passengers/leaving/PAR");
        System.out.println("Query 2: " + responseLeavingParis.body());

        // Query 3: Number of passengers between Calais and Paris
        Response responseBetweenCalaisParis = conductorController.get("/passengers/between?origin=CAL&dest=PAR");
        System.out.println("Query 3: " + responseBetweenCalaisParis.body());

        // Query 4: Who is sitting on chair A11 in service 5160, on December 20th in Calais
        Response responsePassengerA11 = conductorController.get("/passengers/get?location=CAL&serviceId=5159&date=2021-04-01T08:00&seat=H1");
        System.out.println("Query 4: " + responsePassengerA11.body());
    }


}