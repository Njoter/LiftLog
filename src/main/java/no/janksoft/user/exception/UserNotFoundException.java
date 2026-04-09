package no.janksoft.user.exception;

import lombok.Getter;

@Getter
public class UserNotFoundException extends RuntimeException {
    private final String name;

    public UserNotFoundException(String name) {
        super(String.format("Found no user with name: %s", name));
        this.name = name;
    }
}
