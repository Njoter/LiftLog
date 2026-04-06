package no.janksoft.exercise.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import no.janksoft.exercise.dto.CreateExerciseRequest;
import no.janksoft.exercise.dto.ExerciseDetails;
import no.janksoft.exercise.dto.ExerciseSummary;
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

    public ExerciseDetails createExercise(CreateExerciseRequest request) {
        try {
            Exercise exercise = new Exercise(
                    request.name(),
                    request.weightKg(),
                    request.reps(),
                    request.sets()
            );

            Exercise saved = exerciseRepository.save(exercise);
            return toDetails(saved);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateExerciseException(request.name());
        }
    }

    public List<ExerciseSummary> getAllExercises() {
        return exerciseRepository.findAll().stream()
                .map(this::toSummary)
                .toList();
    }

    public ExerciseDetails getExerciseById(Long id) {
        Exercise exercise = exerciseRepository.findById(id)
                .orElseThrow(() -> new ExerciseNotFoundException(id));

        return toDetails(exercise);
    }

    @Transactional
    public ExerciseDetails updateExercise(UpdateExerciseRequest request) {
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

        return toDetails(exercise);
    }

    public void deleteExercise(Long id) {
        if (!exerciseRepository.existsById(id)) {
            throw new ExerciseNotFoundException(id);
        }

        exerciseRepository.deleteById(id);
    }

    private ExerciseDetails toDetails(Exercise exercise) {
        return new ExerciseDetails(
                exercise.getId(),
                exercise.getName(),
                exercise.getWeightKg(),
                exercise.getReps(),
                exercise.getSets()
        );
    }

    private ExerciseSummary toSummary(Exercise exercise) {
        return new ExerciseSummary(
                exercise.getId(),
                exercise.getName(),
                exercise.getWeightKg(),
                exercise.getReps(),
                exercise.getSets()
        );
    }
}
