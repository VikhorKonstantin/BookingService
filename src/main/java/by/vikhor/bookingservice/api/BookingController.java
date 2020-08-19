package by.vikhor.bookingservice.api;

import by.vikhor.bookingservice.entity.Booking;
import by.vikhor.bookingservice.entity.Room;
import by.vikhor.bookingservice.service.BookingService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @SneakyThrows
    @PostMapping
    public ResponseEntity<Booking> bookRoom(@RequestBody Booking booking, UriComponentsBuilder uriComponentsBuilder) {
        Booking newBooking = bookingService.bookRoom(booking);
        URI locationUri =
                uriComponentsBuilder.path("/bookings/")
                        .path(String.valueOf(newBooking.getBookingId()))
                        .build()
                        .toUri();
        return ResponseEntity.created(locationUri).body(newBooking);
    }
}
