package by.vikhor.bookingservice.api;

import by.vikhor.bookingservice.dto.LogInDto;
import by.vikhor.bookingservice.entity.User;
import by.vikhor.bookingservice.security.GrantType;
import by.vikhor.bookingservice.security.jwt.JwtToken;
import by.vikhor.bookingservice.security.jwt.JwtTokenProvider;
import by.vikhor.bookingservice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.net.URI;

@RestController
@RequestMapping("/auth")
@Validated
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtTokenProvider tokenJwtTokenProvider;

    public AuthController(AuthenticationManager authenticationManager,
                          UserService userService, JwtTokenProvider tokenJwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.tokenJwtTokenProvider = tokenJwtTokenProvider;
    }


    @PostMapping(value = "/token", params = "grant_type")
    public ResponseEntity<JwtToken> refreshToken(
            @RequestParam(name = "grant_type") @NotBlank String grantType,
            @RequestParam(name = "refresh_token", required = false) String refreshToken,
            @RequestBody(required = false) LogInDto logInDto) {
        if (grantType.equals(GrantType.REFRESH_TOKEN.getStringValue())) {
            return refreshToken(refreshToken);
        } else if (grantType.equals(GrantType.PASSWORD.getStringValue())) {
            return authenticateUserWithUsernameAndPassword(logInDto);
        }
        throw new BadCredentialsException("Invalid grant_type");
    }

    @PostMapping("/signup")
    public ResponseEntity<User> registerUser(@Valid @RequestBody User user) {
        User registered = userService.register(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/users/{id}")
                .buildAndExpand(registered.getUserId()).toUri();
        return ResponseEntity.created(location).body(registered);
    }

    private ResponseEntity<JwtToken> authenticateUserWithUsernameAndPassword(LogInDto logInDto) {
        if (logInDto == null) {
            throw new BadCredentialsException("Invalid login credentials");
        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        logInDto.getName(),
                        logInDto.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String accessToken = tokenJwtTokenProvider.generateAccessToken(authentication);
        String refreshToken = tokenJwtTokenProvider.generateRefreshToken(authentication);
        return ResponseEntity.ok(new JwtToken(accessToken, refreshToken));
    }

    private ResponseEntity<JwtToken> refreshToken(String refreshToken) {
        boolean isValid = tokenJwtTokenProvider.validateToken(refreshToken);
        if (isValid) {
            Authentication auth =
                    tokenJwtTokenProvider.getUsernamePasswordAuthenticationToken(refreshToken);
            String accessToken = tokenJwtTokenProvider.generateAccessToken(auth);
            String newRefreshToken = tokenJwtTokenProvider.generateRefreshToken(auth);
            return ResponseEntity.ok(new JwtToken(accessToken, newRefreshToken));
        } else {
            throw new BadCredentialsException("Invalid refresh token");
        }
    }
}
