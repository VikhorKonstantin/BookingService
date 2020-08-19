package by.vikhor.bookingservice.service;

import by.vikhor.bookingservice.entity.Booking;

import java.time.LocalDateTime;

public interface BookingService {

    Booking bookRoom(Long roomId, Long userId, LocalDateTime startDateTime, LocalDateTime endDateTime);

    void finishBooking(Long bookingId);

}
