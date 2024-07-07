package com.example.ticketing.service.search;

import com.example.ticketing.exceptions.SeatNotExists;
import com.example.ticketing.model.booking.Booking;
import com.example.ticketing.model.booking.Passenger;
import com.example.ticketing.model.inventory.Route;
import com.example.ticketing.model.inventory.Service;
import com.example.ticketing.model.inventory.Station;
import com.example.ticketing.repository.reservations.ReservationRepository;
import com.example.ticketing.repository.services.ServiceRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class SearchReservationService {

    private final ReservationRepository bookingRepository;
    private final ServiceRepository serviceRepository;

    public SearchReservationService(ReservationRepository bookingRepository, ServiceRepository serviceRepository) {
        this.bookingRepository = bookingRepository;
        this.serviceRepository = serviceRepository;
    }

    public List<Booking> getAllServiceBookings(String serviceId) {
        return bookingRepository.getAll().stream()
                .filter(booking -> booking.getPassengers().stream()
                        .flatMap(passenger -> passenger.getTickets().stream())
                        .anyMatch(ticket -> ticket.getService().getId().equals(serviceId)))
                .collect(Collectors.toList());
    }

    public long getNumberOfPassengersBoarding(String locationCode) {
        return bookingRepository.getAll().stream()
                .flatMap(booking -> booking.getPassengers().stream())
                .flatMap(passenger -> passenger.getTickets().stream())
                .filter(ticket -> ticket.getOrigin().code().equals(locationCode))
                .count();
    }

    public long getNumberOfPassengersLeaving(String locationCode) {
        return bookingRepository.getAll().stream()
                .flatMap(booking -> booking.getPassengers().stream())
                .flatMap(passenger -> passenger.getTickets().stream())
                .filter(ticket -> ticket.getDestination().code().equals(locationCode))
                .count();
    }


    public long getAllPassengersBetween(String originCode, String destinationCode) {
        return bookingRepository.getAll().stream()
                .flatMap(booking -> booking.getPassengers().stream())
                .flatMap(passenger -> passenger.getTickets().stream())
                .filter(ticket -> {
                    final String serviceId = ticket.getService().getId();
                    final Station origin = ticket.getOrigin();
                    final Station destination = ticket.getDestination();
                    final Route route = serviceRepository.get(serviceId).getRoute();

                    return this.isBetween(route, origin, destination, originCode, destinationCode);
                })
                .count();
    }

    private boolean isBetween(Route route, Station ticketOrigin, Station ticketDestination, String originCodeToLookFor, String destinationCodeToLookFor) {
        List<String> stopCodes = route.getStops().stream()
                .map(stop -> stop.station().code())
                .toList();

        int originIndex = stopCodes.indexOf(originCodeToLookFor);
        int destinationIndex = stopCodes.indexOf(destinationCodeToLookFor);

        int ticketOriginIndex = stopCodes.indexOf(ticketOrigin.code());
        int ticketDestinationIndex = stopCodes.indexOf(ticketDestination.code());

        // Check if origin and destination codes are part of the route
        if (originIndex == -1 || destinationIndex == -1) {
            return false;
        }

        // Check if ticket origin and destination are a part of the route checked
        return ticketOriginIndex <= originIndex && ticketDestinationIndex >= destinationIndex;
    }

    public Passenger getPassenger(String serviceId, String date, String seat, String location) throws SeatNotExists {
        final LocalDateTime departureTime = LocalDateTime.parse(date);

        return bookingRepository.getAll().stream()
                .flatMap(booking -> booking.getPassengers().stream())
                .filter(passenger -> passenger.getTickets().stream()
                        .anyMatch(ticket -> {
                            final Service service = ticket.getService();
                            boolean isServiceIdMatch = service.getId().equals(serviceId);
                            boolean isDepartureTimeMatch = service.getDepartureTime().isEqual(departureTime);
                            boolean isSeatMatch = ticket.getSeat().id().equals(seat);
                            boolean isLocationBetweenOrEqual = isLocationBetweenOrEqual(serviceId, location);

                            return isServiceIdMatch && isDepartureTimeMatch && isSeatMatch && isLocationBetweenOrEqual;
                        }))
                .findFirst()
                .orElseThrow(() -> new SeatNotExists("Passenger with seat " + seat + " does not exist"));
    }

    public boolean isLocationBetweenOrEqual(String serviceId, String locationCode) {
        Service service = serviceRepository.get(serviceId);
        if (service == null) {
            return false;
        }
        Route route = service.getRoute();
        List<String> stopCodes = route.getStops().stream()
                .map(stop -> stop.station().code())
                .toList();

        int locationIndex = stopCodes.indexOf(locationCode);

        return bookingRepository.getAll().stream()
                .flatMap(booking -> booking.getPassengers().stream())
                .flatMap(passenger -> passenger.getTickets().stream())
                .anyMatch(ticket -> {
                    int originIndex = stopCodes.indexOf(ticket.getOrigin().code());
                    int destinationIndex = stopCodes.indexOf(ticket.getDestination().code());
                    return locationIndex >= originIndex && locationIndex <= destinationIndex;
                });

    }
}
