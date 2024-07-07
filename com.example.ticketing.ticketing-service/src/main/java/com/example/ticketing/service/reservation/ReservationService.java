package com.example.ticketing.service.reservation;

import com.example.ticketing.exceptions.BookingValidationException;
import com.example.ticketing.model.booking.Booking;
import com.example.ticketing.model.web.BookingRequest;
import com.example.ticketing.model.web.BookingResponse;
import com.example.ticketing.repository.reservations.ReservationRepository;
import com.example.ticketing.service.mapping.BookingMappingService;
import com.example.ticketing.service.validation.RequestValidationService;
import com.example.ticketing.service.seat.SeatAvailabilityService;

public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final RequestValidationService validationService;
    private final SeatAvailabilityService seatAvailabilityService;
    private final BookingMappingService bookingMappingService;

    public ReservationService(ReservationRepository reservationRepository,
                              RequestValidationService validationService,
                              SeatAvailabilityService seatAvailabilityService,
                              BookingMappingService bookingMappingService) {
        this.reservationRepository = reservationRepository;
        this.validationService = validationService;
        this.seatAvailabilityService = seatAvailabilityService;
        this.bookingMappingService = bookingMappingService;
    }

    public BookingResponse reserveSeats(BookingRequest request) {
         try {
             validationService.validate(request);
         } catch (BookingValidationException e) {
             return new BookingResponse(400, e.getDescription());
         } catch (Exception e) {
             return new BookingResponse(503, e.getMessage());
         }

         if (!seatAvailabilityService.areSeatsAvailable(request)) {
             return new BookingResponse(400, "Some of requested seats are not available");
         }

         Booking bookingToSave = bookingMappingService.mapBookingRequest(request);
         Booking savedBooking = reservationRepository.add(bookingToSave);
         return new BookingResponse(201, savedBooking.getId());
    }


}
