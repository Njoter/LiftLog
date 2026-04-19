package no.janksoft.workout.service;

import lombok.RequiredArgsConstructor;
import no.janksoft.exercise.exception.ExerciseNotFoundException;
import no.janksoft.exercise.model.Exercise;
import no.janksoft.exercise.repository.ExerciseRepository;
import no.janksoft.workout.model.WorkoutSet;
import no.janksoft.workout.dto.CreateWorkoutSetRequest;
import no.janksoft.workout.dto.WorkoutSetResponse;
import no.janksoft.workout.exception.WorkoutSetNotFoundException;
import no.janksoft.workout.repository.workoutSetRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkoutService {

    private final workoutSetRepository workoutSetRepository;
    private final ExerciseRepository exerciseRepository;

    public WorkoutSetResponse createWorkoutSet(CreateWorkoutSetRequest request) {
        Exercise exercise = exerciseRepository.findById(request.exerciseId())
                .orElseThrow(() -> new ExerciseNotFoundException(request.exerciseId()));

        WorkoutSet saved = workoutSetRepository.save(
                new WorkoutSet(
                        request.userId(),
                        request.exerciseId(),
                        request.weightKg(),
                        request.reps()
                )
        );

        return toResponse(saved, exercise.getName());
    }

    public List<WorkoutSetResponse> getWorkoutSetsThisWeekByExercise(Long exerciseId) {
        Exercise exercise = exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new ExerciseNotFoundException(exerciseId));

        List<WorkoutSet> workoutSets = workoutSetRepository.findThisWeekByExerciseId(exerciseId);

        String exerciseName = exercise.getName();

        return workoutSets.stream()
                .map(workoutSet -> toResponse(workoutSet, exerciseName))
                .toList();
    }

    public List<WorkoutSetResponse> getWorkoutSetsThisMonthByExercise(Long exerciseId) {
        Exercise exercise = exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new ExerciseNotFoundException(exerciseId));

        List<WorkoutSet> workoutSets = workoutSetRepository.findThisMonthByExerciseId(exerciseId);

        String exerciseName = exercise.getName();

        return workoutSets.stream()
                .map(workoutSet -> toResponse(workoutSet, exerciseName))
                .toList();
    }

    public void deleteWorkoutSet(Long id) {
        WorkoutSet workoutSet = workoutSetRepository.findById(id)
                .orElseThrow(() -> new WorkoutSetNotFoundException(id));

        workoutSetRepository.delete(workoutSet);
    }

    private WorkoutSetResponse toResponse(WorkoutSet workoutSet, String exerciseName) {
        return new WorkoutSetResponse(
                workoutSet.getId(),
                workoutSet.getCreatedAt(),
                exerciseName,
                workoutSet.getExerciseWeightKg(),
                workoutSet.getExerciseReps()
        );
    }
}
