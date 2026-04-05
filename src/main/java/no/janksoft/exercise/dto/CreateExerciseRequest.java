package no.janksoft.exercise.dto;

public record CreateExerciseRequest(
        String name,
        double weightKg,
        int reps,
        int sets
) {}
