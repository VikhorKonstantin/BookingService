package by.vikhor.bookingservice.service.impl;

import by.vikhor.bookingservice.entity.Room;
import by.vikhor.bookingservice.entity.User;
import by.vikhor.bookingservice.repository.UserRepository;
import by.vikhor.bookingservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> findById(Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public User addUser(User newUser) {
        return userRepository.save(newUser);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
}
