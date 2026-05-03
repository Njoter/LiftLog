package no.janksoft.workout.dto;

import java.time.LocalDateTime;

public record WorkoutSetDetails(
        Long id,
        LocalDateTime createdAt,
        Double weightKg,
        Integer reps
) {}
