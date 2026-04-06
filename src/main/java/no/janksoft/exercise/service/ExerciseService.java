package no.janksoft.exercise.service;

import lombok.RequiredArgsConstructor;
import no.janksoft.exercise.dto.CreateExerciseRequest;
import no.janksoft.exercise.dto.ExerciseResponse;
import no.janksoft.exercise.exception.DuplicateExerciseException;
import no.janksoft.exercise.exception.ExerciseNotFoundException;
import no.janksoft.exercise.model.Exercise;
import no.janksoft.exercise.repository.ExerciseRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;

    public ExerciseResponse createExercise(CreateExerciseRequest request) {
        try {
            Exercise exercise = new Exercise(
                    request.name(),
                    request.weightKg(),
                    request.reps(),
                    request.sets()
            );

            Exercise saved = exerciseRepository.save(exercise);
            return toResponse(saved);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateExerciseException(request.name());
        }
    }

    public List<ExerciseResponse> getAllExercises() {
        return exerciseRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public ExerciseResponse getExercise(Long id) {
        Exercise exercise = exerciseRepository.findById(id)
                .orElseThrow(() -> new ExerciseNotFoundException(id));

        return toResponse(exercise);
    }

    private ExerciseResponse toResponse(Exercise exercise) {
        return new ExerciseResponse(
                exercise.getId(),
                exercise.getName(),
                exercise.getWeightKg(),
                exercise.getReps(),
                exercise.getSets()
        );
    }
}
