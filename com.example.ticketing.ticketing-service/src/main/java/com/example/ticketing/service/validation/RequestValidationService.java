package com.example.ticketing.service.validation;

import com.example.ticketing.exceptions.SeatNotExists;
import com.example.ticketing.exceptions.InvalidDateException;
import com.example.ticketing.exceptions.InvalidRouteException;
import com.example.ticketing.exceptions.ServiceNotExistsException;
import com.example.ticketing.model.inventory.Route;
import com.example.ticketing.model.inventory.Seat;
import com.example.ticketing.model.inventory.Service;
import com.example.ticketing.model.web.BookingRequest;
import com.example.ticketing.repository.services.ServiceRepository;

import java.time.LocalDateTime;
import java.util.List;

public class RequestValidationService implements Validator<BookingRequest> {

    private final ServiceRepository serviceRepository;

    public RequestValidationService(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    @Override
    public void validate(BookingRequest item) throws ServiceNotExistsException, InvalidDateException, InvalidRouteException, SeatNotExists {
        final Service requestedService = validateServiceExists(item.getServiceId());
        validateServiceDate(requestedService, item.getServiceDepartureTime());
        validateRoute(requestedService, item.getOriginCode(), item.getDestinationCode());
        validatePassengers(requestedService, item.getPassengers());
    }

    private Service validateServiceExists(String serviceId) throws ServiceNotExistsException {
        if (serviceId == null || serviceId.isEmpty()) {
            throw new ServiceNotExistsException("Service ID is required");
        }

        final Service requestedService = serviceRepository.get(serviceId);
        if (requestedService == null) {
            throw new ServiceNotExistsException("Service with id " + serviceId + " does not exist");
        }

        return requestedService;
    }

    private void validateServiceDate(Service service, LocalDateTime serviceDepartureTime) throws InvalidDateException {
        if (!service.getDepartureTime().isEqual(serviceDepartureTime)) {
            throw new InvalidDateException("Chosen service does not operate on the requested date");
        }
    }

    private void validateRoute(Service service, String originCode, String destinationCode) throws InvalidRouteException {
        final Route route = service.getRoute();
        final List<String> stationCodes = route.getStops().stream()
                .map(stop -> stop.station().code())
                .toList();

        int originIndex = stationCodes.indexOf(originCode);
        int destinationIndex = stationCodes.indexOf(destinationCode);

        if (originIndex == -1 || destinationIndex == -1) {
            throw new InvalidRouteException("Origin or destination does not exist in the service route");
        }

        if (destinationIndex <= originIndex) {
            throw new InvalidRouteException("Destination must come after origin in the service route");
        }
    }

    private void validatePassengers(Service service, List<BookingRequest.Passenger> passengers) throws SeatNotExists {
        for (BookingRequest.Passenger passenger : passengers) {
            validateSeat(service, passenger);
        }
    }

    private void validateSeat(Service service, BookingRequest.Passenger passenger) throws SeatNotExists {
        for (Seat requestedSeat : passenger.getRequestedSeats()) {

            String seatId = requestedSeat.id();
            if (seatId == null || seatId.isEmpty() || !seatId.matches("^[A-Z]\\d+$")) {
                throw new SeatNotExists("Invalid seat ID: " + seatId);
            }
            validateSeatAvailability(service, requestedSeat);
        }
    }

    private void validateSeatAvailability(Service service, Seat requestedSeat) throws SeatNotExists {
        String carriageId = requestedSeat.id().substring(0, 1);

        boolean seatAvailable = service.getCarriages().stream()
                .filter(carriage -> carriage.id().equals(carriageId))
                .flatMap(carriage -> carriage.seats().stream())
                .anyMatch(seat -> seat.id().equals(requestedSeat.id()) && seat.seatClass().equals(requestedSeat.seatClass()));

        if (!seatAvailable) {
            throw new SeatNotExists("Requested seat " + requestedSeat.id() + " with class " + requestedSeat.seatClass() + " does not exist");
        }
    }
}