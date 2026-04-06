package no.janksoft.exercise.dto;

public record ExerciseDetails(
        Long id,
        String name,
        double weightKg,
        int reps,
        int sets
) {}
