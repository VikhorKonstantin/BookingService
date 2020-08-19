package by.vikhor.bookingservice.security;


import by.vikhor.bookingservice.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {
    private final String password;
    private final String username;
    //todo: Add role based auth.
    private static final List<GrantedAuthority> AUTHORITIES = List.of((GrantedAuthority) () -> "ROLE_USER");

    public CustomUserDetails(User user) {
        password = user.getPassword();
        username = user.getName();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AUTHORITIES;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
