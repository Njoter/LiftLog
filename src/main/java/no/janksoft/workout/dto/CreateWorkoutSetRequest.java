package no.janksoft.workout.dto;

public record CreateWorkoutSetRequest(
        Long userId,
        Long exerciseId,
        double weightKg,
        int reps
) {}
