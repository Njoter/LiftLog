package no.janksoft.exercise.service;

import lombok.RequiredArgsConstructor;
import no.janksoft.exercise.dto.CreateExerciseRequest;
import no.janksoft.exercise.dto.ExerciseResponse;
import no.janksoft.exercise.model.Exercise;
import no.janksoft.exercise.repository.ExerciseRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;

    public ExerciseResponse createExercise(CreateExerciseRequest request) {
        Exercise exercise = new Exercise(
                request.name(),
                request.weightKg(),
                request.reps(),
                request.sets()
        );

        Exercise saved = exerciseRepository.save(exercise);

        return new ExerciseResponse(
                saved.getName(),
                saved.getWeightKg(),
                saved.getReps(),
                saved.getSets()
        );
    }
}
