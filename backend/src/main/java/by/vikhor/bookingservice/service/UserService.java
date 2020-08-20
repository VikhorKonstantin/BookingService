package by.vikhor.bookingservice.service;

import by.vikhor.bookingservice.entity.User;
import by.vikhor.bookingservice.service.exception.GenericBadRequestException;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService extends UserDetailsService {
    User register(User userDto) throws GenericBadRequestException;

    Optional<User> findById(Long userId);
}
