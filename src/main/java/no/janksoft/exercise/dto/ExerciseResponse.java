package no.janksoft.exercise.dto;

public record ExerciseResponse(
        String name,
        double weightKg,
        int reps,
        int sets
) {}
