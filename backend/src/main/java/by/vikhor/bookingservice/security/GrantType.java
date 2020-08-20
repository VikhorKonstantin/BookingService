package by.vikhor.bookingservice.security;

import lombok.Getter;

@Getter
public enum GrantType {
    PASSWORD("password"),
    REFRESH_TOKEN("refresh_token");

    private final String stringValue;

    GrantType(String stringValue) {
        this.stringValue = stringValue;
    }
}
