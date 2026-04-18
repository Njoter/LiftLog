package no.janksoft.workout.controller;

import lombok.RequiredArgsConstructor;
import no.janksoft.workout.service.WorkoutService;
import no.janksoft.workout.dto.CreateWorkoutSetRequest;
import no.janksoft.workout.dto.WorkoutSetResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
