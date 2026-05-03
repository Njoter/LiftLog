package no.janksoft.workout.service;

import lombok.RequiredArgsConstructor;
import no.janksoft.exercise.exception.ExerciseNotFoundException;
import no.janksoft.exercise.model.Exercise;
import no.janksoft.exercise.repository.ExerciseRepository;
import no.janksoft.workout.dto.WorkoutSetResponse;
import no.janksoft.workout.model.WorkoutSet;
import no.janksoft.workout.dto.CreateWorkoutSetRequest;
import no.janksoft.workout.dto.WorkoutSetDetails;
import no.janksoft.workout.exception.WorkoutSetNotFoundException;
import no.janksoft.workout.repository.workoutSetRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkoutService {

    private final workoutSetRepository workoutSetRepository;
    private final ExerciseRepository exerciseRepository;

    public WorkoutSetDetails createWorkoutSet(CreateWorkoutSetRequest request) {
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

        return toDetails(saved);
    }

    public WorkoutSetResponse getWorkoutSetsByExerciseThisWeek(Long exerciseId) {
        Exercise exercise = exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new ExerciseNotFoundException(exerciseId));

        List<WorkoutSetDetails> workoutSets = workoutSetRepository.findThisWeekByExerciseId(exerciseId)
                .stream()
                .map(this::toDetails)
                .toList();

        return toListResponse(workoutSets, exercise);
    }

    public WorkoutSetResponse getWorkoutSetsByExerciseThisMonth(Long exerciseId) {
        Exercise exercise = exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new ExerciseNotFoundException(exerciseId));

        List<WorkoutSetDetails> workoutSets = workoutSetRepository.findThisMonthByExerciseId(exerciseId)
                .stream()
                .map(this::toDetails)
                .toList();

        return toListResponse(workoutSets, exercise);
    }

    public void deleteWorkoutSet(Long id) {
        WorkoutSet workoutSet = workoutSetRepository.findById(id)
                .orElseThrow(() -> new WorkoutSetNotFoundException(id));

        workoutSetRepository.delete(workoutSet);
    }

    private WorkoutSetDetails toDetails(WorkoutSet workoutSet) {
        return new WorkoutSetDetails(
                workoutSet.getId(),
                workoutSet.getCreatedAt(),
                workoutSet.getExerciseWeightKg(),
                workoutSet.getExerciseReps()
        );
    }

    private WorkoutSetResponse toListResponse(List<WorkoutSetDetails> workoutSets, Exercise exercise) {
        return new WorkoutSetResponse(
                exercise.getId(),
                exercise.getName(),
                workoutSets,
                totalReps(workoutSets),
                workoutSets.size(),
                !workoutSets.isEmpty() ? workoutSets.get(0).createdAt() : null
        );
    }

    private int totalReps(List<WorkoutSetDetails> workoutSets) {
        return workoutSets.stream()
                .mapToInt(WorkoutSetDetails::reps)
                .sum();
    }
}
