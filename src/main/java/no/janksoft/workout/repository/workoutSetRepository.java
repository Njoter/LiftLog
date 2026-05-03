package no.janksoft.workout.repository;

import no.janksoft.workout.model.WorkoutSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface workoutSetRepository extends JpaRepository<WorkoutSet, Long> {

    @Query(value = """
            SELECT * FROM workout_sets
            WHERE created_at >= DATE_TRUNC('week', CURRENT_DATE)
            AND exercise_id = :exerciseId
            ORDER BY created_at""",
            nativeQuery = true
    )
    List<WorkoutSet> findThisWeekByExerciseId(Long exerciseId);

    @Query(value = """
            SELECT * FROM workout_sets
            WHERE created_at >= DATE_TRUNC('month', CURRENT_DATE)
            AND exercise_id = :exerciseId
            ORDER BY created_at""",
            nativeQuery = true
    )
    List<WorkoutSet> findThisMonthByExerciseId(Long exerciseId);
}
