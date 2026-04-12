package no.janksoft.exercise.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import no.janksoft.exercise.dto.CreateExerciseRequest;
import no.janksoft.exercise.dto.ExerciseDetails;
import no.janksoft.exercise.dto.ExerciseSummary;
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
    public ResponseEntity<ExerciseDetails> createExercise(
            @Valid @RequestBody CreateExerciseRequest request
    ) {
        ExerciseDetails response = exerciseService.createExercise(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("user/{userId}")
    public ResponseEntity<List<ExerciseSummary>> getAllExercisesByUser(
            @PathVariable Long userId
    ) {
        List<ExerciseSummary> response = exerciseService.getAllExercisesByUser(userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<ExerciseDetails> getExerciseById(
            @PathVariable Long id
    ) {
        ExerciseDetails response = exerciseService.getExerciseById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<ExerciseDetails> updateExercise(
            @Valid @RequestBody UpdateExerciseRequest request
    ) {
        ExerciseDetails response = exerciseService.updateExercise(request);
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