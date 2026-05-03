package no.janksoft.workout.controller;

import lombok.RequiredArgsConstructor;
import no.janksoft.workout.dto.WorkoutSetResponse;
import no.janksoft.workout.service.WorkoutService;
import no.janksoft.workout.dto.CreateWorkoutSetRequest;
import no.janksoft.workout.dto.WorkoutSetDetails;
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
    public ResponseEntity<WorkoutSetDetails> createWorkoutSet(
            @RequestBody CreateWorkoutSetRequest request
    ) {
        WorkoutSetDetails response = workoutService.createWorkoutSet(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("by-exercise/{exerciseId}/week")
    public ResponseEntity<WorkoutSetResponse> getWorkoutSetsByExerciseThisWeek(
            @PathVariable Long exerciseId
    ) {
        WorkoutSetResponse response = workoutService.getWorkoutSetsByExerciseThisWeek(exerciseId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("by-exercise/{exerciseId}/month")
    public ResponseEntity<WorkoutSetResponse> getWorkoutSetsByExerciseThisMonth(
            @PathVariable Long exerciseId
    ) {
        WorkoutSetResponse response = workoutService.getWorkoutSetsByExerciseThisMonth(exerciseId);
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
