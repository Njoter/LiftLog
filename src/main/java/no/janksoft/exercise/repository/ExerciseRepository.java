package no.janksoft.exercise.repository;

import no.janksoft.exercise.model.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    boolean existsByName(String name);
}
