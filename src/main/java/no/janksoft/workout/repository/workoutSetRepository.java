package no.janksoft.workout.repository;

import no.janksoft.workout.model.WorkoutSet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface workoutSetRepository extends JpaRepository<WorkoutSet, Long> {
}
