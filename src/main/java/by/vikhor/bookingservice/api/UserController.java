package by.vikhor.bookingservice.api;

import by.vikhor.bookingservice.entity.User;
import by.vikhor.bookingservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User user, UriComponentsBuilder uriComponentsBuilder) {
        User newUser = userService.addUser(user);
        URI locationUri =
                uriComponentsBuilder.path("/users/")
                        .path(String.valueOf(newUser.getUserId()))
                        .build()
                        .toUri();
        return ResponseEntity.created(locationUri).body(newUser);
    }
}
