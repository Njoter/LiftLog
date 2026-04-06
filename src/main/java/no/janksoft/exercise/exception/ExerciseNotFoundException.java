package no.janksoft.exercise.exception;

import lombok.Getter;

@Getter
public class ExerciseNotFoundException extends RuntimeException {
    private final Long id;

    public ExerciseNotFoundException(Long id) {
        super(String.format("Found no exercise with ID: %s", id));
        this.id = id;
    }
}
