package by.vikhor.bookingservice.security.jwt;

import by.vikhor.bookingservice.security.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
@PropertySource("classpath:security.properties")
public class JwtTokenProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenProvider.class);
    private final SecretKey jwtSecret;
    private final Long jwtAccessTokenExpirationInMs;
    private final Long jwtRefreshTokenExpirationInMs;
    private final UserDetailsService userDetailsService;

    @Autowired
    public JwtTokenProvider(@Value("${jwt.jwtSecret}") String jwtSecretString,
                            @Value("${jwt.jwtAccessTokenExpirationInMs}") Long jwtAccessTokenExpirationInMs,
                            @Value("${jwt.jwtRefreshTokenExpirationInMs}") Long jwtRefreshTokenExpirationInMs,
                            UserDetailsService userDetailsService) {
        this.jwtSecret = Keys.hmacShaKeyFor(jwtSecretString.getBytes());
        this.jwtAccessTokenExpirationInMs = jwtAccessTokenExpirationInMs;
        this.jwtRefreshTokenExpirationInMs = jwtRefreshTokenExpirationInMs;
        this.userDetailsService = userDetailsService;
    }

    public String generateAccessToken(Authentication authentication) {
        return generateToken(authentication, jwtAccessTokenExpirationInMs);
    }

    public String generateRefreshToken(Authentication authentication) {
        return generateToken(authentication, jwtRefreshTokenExpirationInMs);
    }

    public UsernamePasswordAuthenticationToken getUsernamePasswordAuthenticationToken(String jwt) {
        String userName = getUserNameFromJwt(jwt);
        UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
        return new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
    }

    private String generateToken(Authentication authentication, Long jwtExpirationInMs) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        LocalDateTime expiryDate = LocalDateTime.now().plus(jwtExpirationInMs, ChronoUnit.MILLIS);
        ZonedDateTime zonedDateTime = ZonedDateTime.of(expiryDate, ZoneId.systemDefault());
        Instant result = Instant.from(zonedDateTime);
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(Date.from(result))
                .signWith(jwtSecret)
                .compact();
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(jwtSecret).build().parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException ex) {
            LOGGER.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            LOGGER.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            LOGGER.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            LOGGER.error("JWT claims string is empty.");
        }
        return false;
    }

    private String getUserNameFromJwt(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(jwtSecret)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
}
