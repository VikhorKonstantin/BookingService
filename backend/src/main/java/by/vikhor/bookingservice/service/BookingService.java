package by.vikhor.bookingservice.service;

import by.vikhor.bookingservice.entity.Booking;
import by.vikhor.bookingservice.service.exception.GenericBadRequestException;
import by.vikhor.bookingservice.service.exception.ResourceNotFoundException;

public interface BookingService {

    Booking bookRoom(Booking booking) throws GenericBadRequestException, ResourceNotFoundException;

    void completeBooking(Long bookingId) throws ResourceNotFoundException, GenericBadRequestException;

}
