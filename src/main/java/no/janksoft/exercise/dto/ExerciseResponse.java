package no.janksoft.exercise.dto;

public record ExerciseResponse(
        Long id,
        String name,
        double weightKg,
        int reps,
        int sets
) {}
