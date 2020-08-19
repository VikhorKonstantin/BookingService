package by.vikhor.bookingservice.api;

import by.vikhor.bookingservice.entity.Room;
import by.vikhor.bookingservice.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    private final RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping
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
    public ResponseEntity<List<Room>> findAll() {
        return ResponseEntity.ok(roomService.findAllRooms());
    }
}
