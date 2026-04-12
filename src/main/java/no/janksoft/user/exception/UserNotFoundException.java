package no.janksoft.user.exception;

import lombok.Getter;

@Getter
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("User not found");
    }
}
