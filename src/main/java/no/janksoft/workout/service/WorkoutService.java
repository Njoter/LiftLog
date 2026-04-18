package no.janksoft.workout.service;

import lombok.RequiredArgsConstructor;
import no.janksoft.exercise.exception.ExerciseNotFoundException;
import no.janksoft.exercise.model.Exercise;
import no.janksoft.exercise.repository.ExerciseRepository;
import no.janksoft.workout.dto.WorkoutSet;
import no.janksoft.workout.dto.CreateWorkoutSetRequest;
import no.janksoft.workout.dto.WorkoutSetResponse;
import no.janksoft.workout.repository.WorkoutRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorkoutService {

    private final WorkoutRepository workoutRepository;
    private final ExerciseRepository exerciseRepository;

    public WorkoutSetResponse createWorkoutSet(CreateWorkoutSetRequest request) {
        Exercise exercise = exerciseRepository.findById(request.exerciseId())
                .orElseThrow(() -> new ExerciseNotFoundException(request.exerciseId()));

        WorkoutSet saved = workoutRepository.save(
                new WorkoutSet(
                        request.userId(),
                        request.exerciseId(),
                        request.weightKg(),
                        request.reps()
                )
        );

        return toResponse(saved, exercise);
    }

    private WorkoutSetResponse toResponse(WorkoutSet workoutSet, Exercise exercise) {
        return new WorkoutSetResponse(
                workoutSet.getId(),
                workoutSet.getCreatedAt(),
                exercise.getName(),
                workoutSet.getExerciseWeightKg(),
                workoutSet.getExerciseReps()
        );
    }
}
