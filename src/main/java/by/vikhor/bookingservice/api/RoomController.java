package by.vikhor.bookingservice.api;

import by.vikhor.bookingservice.config.BookingServiceAppConfig;
import by.vikhor.bookingservice.entity.Booking;
import by.vikhor.bookingservice.entity.Room;
import by.vikhor.bookingservice.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    private final RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    //todo:: fix
    @PostMapping
    //@PreAuthorize("hasRole('USER')")
    @PreAuthorize("permitAll()")
    public ResponseEntity<Room> addRoom(@RequestBody Room room, UriComponentsBuilder uriComponentsBuilder) {
        Room newRoom = roomService.addRoom(room);
        URI locationUri =
                uriComponentsBuilder.path("/rooms/")
                        .path(String.valueOf(newRoom.getRoomId()))
                        .build()
                        .toUri();
        return ResponseEntity.created(locationUri).body(newRoom);
    }

    @GetMapping
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<Room>> findAll() {
        return ResponseEntity.ok(roomService.findAllRooms());
    }

    @GetMapping("/free")
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<Room>> findFreeInRange(
            @RequestParam @DateTimeFormat(pattern = BookingServiceAppConfig.DATE_TIME_FORMAT) LocalDateTime from,
            @RequestParam @DateTimeFormat(pattern = BookingServiceAppConfig.DATE_TIME_FORMAT) LocalDateTime to) {
        return ResponseEntity.ok(roomService.findFreeInRange(from, to));
    }
}
