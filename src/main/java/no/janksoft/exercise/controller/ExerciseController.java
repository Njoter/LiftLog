package no.janksoft.exercise.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import no.janksoft.exercise.dto.CreateExerciseRequest;
import no.janksoft.exercise.dto.ExerciseResponse;
import no.janksoft.exercise.dto.UpdateExerciseRequest;
import no.janksoft.exercise.service.ExerciseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.base-path}/exercises")
@RequiredArgsConstructor
public class ExerciseController {

    private final ExerciseService exerciseService;

    @PostMapping
    public ResponseEntity<ExerciseResponse> createExercise(
            @Valid @RequestBody CreateExerciseRequest request
    ) {
        ExerciseResponse response = exerciseService.createExercise(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ExerciseResponse>> getAllExercises() {
        List<ExerciseResponse> response = exerciseService.getAllExercises();
        return ResponseEntity.ok(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<ExerciseResponse> getExerciseById(
            @PathVariable Long id
    ) {
        ExerciseResponse response = exerciseService.getExerciseById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<ExerciseResponse> updateExercise(
            @Valid @RequestBody UpdateExerciseRequest request
    ) {
        ExerciseResponse response = exerciseService.updateExercise(request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteExercise(
            @PathVariable Long id
    ) {
        exerciseService.deleteExercise(id);
        return ResponseEntity.noContent().build();
    }
}