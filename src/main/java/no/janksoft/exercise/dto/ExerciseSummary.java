package no.janksoft.exercise.dto;

public record ExerciseSummary(
        Long id,
        String name,
        double weightKg,
        int reps,
        int sets
) {}
