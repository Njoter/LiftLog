package no.janksoft.workout.dto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "workout_sets")
public class WorkoutSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "exerciseId", nullable = false)
    private Long exerciseId;

    @Column(name = "exercise_weightKg", nullable = false)
    private Double exerciseWeightKg;

    @Column(name = "exercise_reps", nullable = false)
    private Integer exerciseReps;

    private LocalDateTime createdAt;

    public WorkoutSet(
            Long userId,
            Long exerciseId,
            Double exerciseWeightKg,
            Integer exerciseReps
    ) {
        this.userId = userId;
        this.exerciseId = exerciseId;
        this.exerciseWeightKg = exerciseWeightKg;
        this.exerciseReps = exerciseReps;
        this.createdAt = LocalDateTime.now();
    }
}
