package no.janksoft.exercise.repository;

import no.janksoft.exercise.model.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    boolean existsByName(String name);
    List<Exercise> findAllByUserId(Long userId);
}
