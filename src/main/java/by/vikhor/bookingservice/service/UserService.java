package by.vikhor.bookingservice.service;

import by.vikhor.bookingservice.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService extends UserDetailsService {
    User register(User userDto);
    Optional<User> findById(Long userId);
}
