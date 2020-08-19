package by.vikhor.bookingservice.service;

import by.vikhor.bookingservice.entity.Booking;
import by.vikhor.bookingservice.service.exception.BookingConflictException;
import by.vikhor.bookingservice.service.exception.ResourceNotFoundException;

public interface BookingService {

    Booking bookRoom(Booking booking) throws BookingConflictException, ResourceNotFoundException;

    void finishBooking(Long bookingId);

}
