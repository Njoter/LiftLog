package no.janksoft.workout.controller;

import lombok.RequiredArgsConstructor;
import no.janksoft.workout.model.WorkoutSet;
import no.janksoft.workout.service.WorkoutService;
import no.janksoft.workout.dto.CreateWorkoutSetRequest;
import no.janksoft.workout.dto.WorkoutSetResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.base-path}/workout")
@RequiredArgsConstructor
public class WorkoutController {

    private final WorkoutService workoutService;

    @PostMapping
    public ResponseEntity<WorkoutSetResponse> createWorkoutSet(
            @RequestBody CreateWorkoutSetRequest request
    ) {
        WorkoutSetResponse response = workoutService.createWorkoutSet(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("by-exercise/{exerciseId}/week")
    public ResponseEntity<List<WorkoutSetResponse>> getWorkoutSetsThisWeekByExercise(
            @PathVariable Long exerciseId
    ) {
        List<WorkoutSetResponse> response = workoutService.getWorkoutSetsThisWeekByExercise(exerciseId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("by-exercise/{exerciseId}/month")
    public ResponseEntity<List<WorkoutSetResponse>> getWorkoutSetsThisMonthByExercise(
            @PathVariable Long exerciseId
    ) {
        List<WorkoutSetResponse> response = workoutService.getWorkoutSetsThisMonthByExercise(exerciseId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteWorkoutSet(
            @PathVariable Long id
    ) {
        workoutService.deleteWorkoutSet(id);
        return ResponseEntity.noContent().build();
    }
}
