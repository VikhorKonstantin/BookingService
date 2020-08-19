package by.vikhor.bookingservice.service;

import by.vikhor.bookingservice.entity.Room;
import by.vikhor.bookingservice.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> findById(Long userId);

    User addUser(User newUser);

    List<User> findAllUsers();

}
