package by.vikhor.bookingservice.service;

import by.vikhor.bookingservice.entity.Room;

import java.util.List;

public interface RoomService {

    Room addRoom(Room newRoom);

    List<Room> findAllRooms();

}
