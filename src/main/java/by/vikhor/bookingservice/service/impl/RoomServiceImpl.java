package by.vikhor.bookingservice.service.impl;

import by.vikhor.bookingservice.entity.Room;
import by.vikhor.bookingservice.repository.RoomRepository;
import by.vikhor.bookingservice.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    @Autowired
    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public Room addRoom(Room newRoom) {
        return roomRepository.save(newRoom);
    }

    @Override
    public List<Room> findAllRooms() {
        return roomRepository.findAll();
    }
}
