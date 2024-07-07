package com.example.ticketing.service.validation;

import com.example.ticketing.exceptions.ServiceNotExistsException;
import com.example.ticketing.model.inventory.*;
import com.example.ticketing.model.web.BookingRequest;
import com.example.ticketing.repository.services.ServiceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class RequestValidationServiceTest {

    private ServiceRepository serviceRepository;
    private RequestValidationService validationService;

    @BeforeEach
    void setUp() {
        serviceRepository = Mockito.mock(ServiceRepository.class);
        validationService = new RequestValidationService(serviceRepository);
    }

    @Test
    void validateCompleteRequest_validRequest() {
        LocalDateTime departureTime = LocalDateTime.now();
        Route route = new Route.Builder()
                .id("routeId")
                .stop(new Stop(new Station("ORIGIN", ""), 0))
                .stop(new Stop(new Station("DEST", ""), 1))
                .build();
        Service mockService = new Service.Builder()
                .id( "validServiceId")
                .departureTime(departureTime)
                .route(route)
                .carriages(List.of(new Carriage("A", List.of(new Seat("1", SeatClass.FIRST_CLASS)))))
                .build();

        when(serviceRepository.get("validServiceId")).thenReturn(mockService);

        BookingRequest request = new BookingRequest("validServiceId", departureTime, "ORIGIN", "DEST", List.of(new BookingRequest.Passenger("P1", List.of(new Seat("A1", SeatClass.FIRST_CLASS)))));
        assertDoesNotThrow(() -> validationService.validate(request));
    }

    @Test
    void validateCompleteRequest_invalidServiceId() {
        when(serviceRepository.get("invalidServiceId")).thenReturn(null);

        BookingRequest request = new BookingRequest("invalidServiceId", LocalDateTime.now(), "ORIGIN", "DEST", List.of(new BookingRequest.Passenger("P1", List.of(new Seat("A1", SeatClass.FIRST_CLASS)))));
        assertThrows(ServiceNotExistsException.class, () -> validationService.validate(request));
    }
}