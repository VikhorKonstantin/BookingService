package by.vikhor.bookingservice.api;

import by.vikhor.bookingservice.entity.Booking;
import by.vikhor.bookingservice.service.BookingService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;

import static by.vikhor.bookingservice.api.ApiConstants.API_BASE_URL;

@RestController
@RequestMapping(API_BASE_URL + "/bookings")
@Validated
public class BookingController {

    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @SneakyThrows

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Booking> bookRoom(@RequestBody @Valid Booking booking,
                                            UriComponentsBuilder uriComponentsBuilder) {
        Booking newBooking = bookingService.bookRoom(booking);
        URI locationUri =
                uriComponentsBuilder.path("/bookings/")
                        .path(String.valueOf(newBooking.getBookingId()))
                        .build()
                        .toUri();
        return ResponseEntity.created(locationUri).body(newBooking);
    }

    @SneakyThrows
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void completeBooking(@PathVariable @Positive Long id) {
        bookingService.completeBooking(id);
    }
}
