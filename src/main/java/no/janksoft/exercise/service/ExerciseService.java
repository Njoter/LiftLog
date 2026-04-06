package no.janksoft.exercise.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import no.janksoft.exercise.dto.CreateExerciseRequest;
import no.janksoft.exercise.dto.ExerciseResponse;
import no.janksoft.exercise.dto.UpdateExerciseRequest;
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

    public ExerciseResponse getExerciseById(Long id) {
        Exercise exercise = exerciseRepository.findById(id)
                .orElseThrow(() -> new ExerciseNotFoundException(id));

        return toResponse(exercise);
    }

    @Transactional
    public ExerciseResponse updateExercise(UpdateExerciseRequest request) {
        Exercise exercise = exerciseRepository.findById(request.id())
                .orElseThrow(() -> new ExerciseNotFoundException(request.id()));

        // Check for duplicate name (excluding current exercise)
        if (!exercise.getName().equals(request.name()) &&
                exerciseRepository.existsByName(request.name())) {
            throw new DuplicateExerciseException(request.name());
        }

        exercise.setName(request.name());
        exercise.setWeightKg(request.weightKg());
        exercise.setReps(request.reps());
        exercise.setSets(request.sets());

        return toResponse(exercise);
    }

    public void deleteExercise(Long id) {
        if (!exerciseRepository.existsById(id)) {
            throw new ExerciseNotFoundException(id);
        }

        exerciseRepository.deleteById(id);
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
