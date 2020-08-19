package by.vikhor.bookingservice.service.impl;

import by.vikhor.bookingservice.entity.Booking;
import by.vikhor.bookingservice.entity.Room;
import by.vikhor.bookingservice.entity.User;
import by.vikhor.bookingservice.repository.BookingRepository;
import by.vikhor.bookingservice.service.BookingService;
import by.vikhor.bookingservice.service.RoomService;
import by.vikhor.bookingservice.service.UserService;
import by.vikhor.bookingservice.service.exception.BookingConflictException;
import by.vikhor.bookingservice.service.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Booking bookRoom(Booking booking) throws BookingConflictException, ResourceNotFoundException {
        if (isNotValidTimeRange(booking)) {
            throw new IllegalArgumentException("Invalid time range. The start date must be before the end date.");
        }
        Room room = roomService.findById(booking.getRoomId())
                .orElseThrow(ResourceNotFoundException::new);
        booking.setRoom(room);
        User user = userService.findById(booking.getRoomId())
                .orElseThrow(ResourceNotFoundException::new);
        booking.setUser(user);
        if (bookingRepository.isConflictNotExists(
                booking.getRoomId(), booking.getUserId(), booking.getStart(), booking.getEnd())) {
            return bookingRepository.save(booking);
        } else {
            throw new BookingConflictException("Can't book this room because of time conflicts");
        }
    }

    @Override
    public void finishBooking(Long bookingId) {

    }

    private boolean isNotValidTimeRange(Booking booking) {
        return booking.getStart().compareTo(booking.getEnd()) >= 0;
    }
}
