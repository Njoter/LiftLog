package no.janksoft.workout.dto;

import java.time.LocalDateTime;

public record WorkoutSetResponse(
        Long id,
        LocalDateTime createdAt,
        String exerciseName,
        Double weightKg,
        Integer reps
) {}
