package no.janksoft.workout.dto;

import java.time.LocalDateTime;
import java.util.List;

public record WorkoutSetResponse(
        Long exerciseId,
        String exerciseName,
        List<WorkoutSetDetails> workoutSets,
        int totalReps,
        int totalSets,
        LocalDateTime lastRecorded
) {}
