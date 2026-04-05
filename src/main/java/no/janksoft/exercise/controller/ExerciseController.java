package no.janksoft.exercise.controller;

import lombok.RequiredArgsConstructor;
import no.janksoft.exercise.dto.CreateExerciseRequest;
import no.janksoft.exercise.dto.ExerciseResponse;
import no.janksoft.exercise.service.ExerciseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.base-path}/exercises")
@RequiredArgsConstructor
public class ExerciseController {

    private final ExerciseService exerciseService;

    @PostMapping
    public ResponseEntity<ExerciseResponse> createExercise(
            @RequestBody CreateExerciseRequest request
    ) {
        ExerciseResponse response = exerciseService.createExercise(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}