package by.vikhor.bookingservice.repository

import by.vikhor.bookingservice.entity.Booking
import by.vikhor.bookingservice.entity.Room
import by.vikhor.bookingservice.entity.User

import java.time.LocalDateTime

class TestDataProvider {
    static def
    insertTestData(RoomRepository roomRepository, UserRepository userRepository, BookingRepository bookingRepository) {
        roomRepository.save(new Room(null, "Room 1"))
        roomRepository.save(new Room(null, "Room 2"))
        roomRepository.save(new Room(null, "Room 3"))
        userRepository.save(new User(null, "user 1", "role", "pwd"))
        userRepository.save(new User(null, "user 2", "role", "pwd"))
        userRepository.save(new User(null, "user 3", "role", "pwd"))
        bookingRepository.save(new Booking(null, null, 1, null, 1,
                LocalDateTime.parse("2020-12-12T20:12:12"),
                LocalDateTime.parse("2020-12-12T20:20:12"), "descr"))
        bookingRepository.save(new Booking(null, null, 2, null, 2,
                LocalDateTime.parse("2020-12-12T20:12:12"),
                LocalDateTime.parse("2020-12-12T20:20:12"), "descr"))
        bookingRepository.save(new Booking(null, null, 3, null, 2,
                LocalDateTime.parse("2021-12-12T20:12:12"),
                LocalDateTime.parse("2021-12-12T20:20:12"), "descr"))
    }
}
