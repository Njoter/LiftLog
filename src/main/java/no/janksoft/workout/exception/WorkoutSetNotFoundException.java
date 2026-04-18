package no.janksoft.workout.exception;

import lombok.Getter;

@Getter
public class WorkoutSetNotFoundException extends RuntimeException {
    private Long id;

    public WorkoutSetNotFoundException(Long id) {
        super(String.format("Found no workout set with ID: %s", id));
    }
}
