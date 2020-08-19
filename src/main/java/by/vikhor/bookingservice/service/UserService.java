package by.vikhor.bookingservice.service;

import by.vikhor.bookingservice.entity.User;

import java.util.List;

public interface UserService {

    User addUser(User newUser);

    List<User> findAllUsers();

}
