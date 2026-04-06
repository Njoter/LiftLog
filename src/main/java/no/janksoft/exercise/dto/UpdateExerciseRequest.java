package no.janksoft.exercise.dto;

import jakarta.validation.constraints.*;

public record UpdateExerciseRequest(
        @NotNull(message = "Exercise ID is required")
        Long id,

        @NotBlank(message = "Exercise name is required")
        @Size(max = 50, message = "Exercise name must be less than 50 characters")
        String name,

        @PositiveOrZero(message = "Weight must be 0 or greater")
        @Digits(integer = 4, fraction = 2, message = "Weight may have up to 4 digits and 2 decimal places")
        double weightKg,

        @Positive(message = "At least 1 rep required")
        @Max(value = 999, message = "Reps cannot exceed 999")
        int reps,

        @Positive(message = "At least 1 set required")
        @Max(value = 999, message = "Sets cannot exceed 999")
        int sets
) {}
