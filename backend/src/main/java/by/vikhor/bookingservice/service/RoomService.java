package by.vikhor.bookingservice.service;

import by.vikhor.bookingservice.entity.Room;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RoomService {

    Optional<Room> findById(Long roomId);

    Room addRoom(Room newRoom);

    List<Room> findAllRooms();

    List<Room> findFreeInRange(LocalDateTime from, LocalDateTime to);

}
