package no.janksoft.exercise.exception;

import lombok.Getter;

@Getter
public class DuplicateExerciseException extends RuntimeException {
    private final String name;

    public DuplicateExerciseException(String name) {
        super(String.format("Exercise with name '%s' already exists", name));
        this.name = name;
    }
}
