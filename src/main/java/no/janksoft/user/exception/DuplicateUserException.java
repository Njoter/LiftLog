package no.janksoft.user.exception;

import lombok.Getter;

@Getter
public class DuplicateUserException extends RuntimeException {
    private final String name;

    public DuplicateUserException(String name) {
        super(String.format("User with name '%s' already exists", name));
        this.name = name;
    }
}
