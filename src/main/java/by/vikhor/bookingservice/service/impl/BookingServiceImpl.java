package by.vikhor.bookingservice.service.impl;

import by.vikhor.bookingservice.entity.Booking;
import by.vikhor.bookingservice.entity.Room;
import by.vikhor.bookingservice.entity.User;
import by.vikhor.bookingservice.repository.BookingRepository;
import by.vikhor.bookingservice.service.BookingService;
import by.vikhor.bookingservice.service.RoomService;
import by.vikhor.bookingservice.service.UserService;
import by.vikhor.bookingservice.service.exception.GenericBadRequestException;
import by.vikhor.bookingservice.service.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final RoomService roomService;
    private final UserService userService;

    @Autowired
    public BookingServiceImpl(BookingRepository bookingRepository, RoomService roomService, UserService userService) {
        this.bookingRepository = bookingRepository;
        this.roomService = roomService;
        this.userService = userService;
    }

    @Override
    @Transactional
    public Booking bookRoom(Booking booking) throws GenericBadRequestException, ResourceNotFoundException {
        if (isNotValidTimeRange(booking)) {
            throw new GenericBadRequestException("Invalid time range. The start date must be before the end date.");
        }
        Room room = roomService.findById(booking.getRoomId())
                .orElseThrow(ResourceNotFoundException::new);
        booking.setRoom(room);
        User user = userService.findById(booking.getUserId())
                .orElseThrow(ResourceNotFoundException::new);
        booking.setUser(user);
        if (bookingRepository.isConflictNotExists(
                booking.getRoomId(), booking.getUserId(), booking.getStart(), booking.getEnd())) {
            return bookingRepository.save(booking);
        } else {
            throw new GenericBadRequestException("Can't book this room because of time conflicts");
        }
    }

    @Override
    @Transactional
    public void completeBooking(Long bookingId) throws ResourceNotFoundException, GenericBadRequestException {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(ResourceNotFoundException::new);
        LocalDateTime now = LocalDateTime.now();
        if (hasNotCompleted(booking, now)) {
            completeIfAlreadyStartedOrElseDelete(booking, now);
        } else {
            throw new GenericBadRequestException("This booking is already completed.");
        }
    }

    private void completeIfAlreadyStartedOrElseDelete(Booking booking, LocalDateTime now) {
        if (hasAlreadyStarted(booking, now)) {
            booking.setEnd(now);
            bookingRepository.save(booking);
        } else {
            bookingRepository.delete(booking);
        }
    }

    private boolean hasAlreadyStarted(Booking booking, LocalDateTime now) {
        return booking.getStart().compareTo(now) <= 0;
    }

    private boolean hasNotCompleted(Booking booking, LocalDateTime now) {
        return booking.getEnd().compareTo(now) > 0;
    }

    private boolean isNotValidTimeRange(Booking booking) {
        return booking.getStart().compareTo(booking.getEnd()) >= 0;
    }
}
