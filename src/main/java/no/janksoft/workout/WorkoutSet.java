package no.janksoft.workout;

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

    @Column(name = "exercise_name", nullable = false)
    private String exerciseName;

    @Column(name = "exercise_weightKg", nullable = false)
    private Double exerciseWeightKg;

    @Column(name = "exercise_reps", nullable = false)
    private Integer exerciseReps;

    private LocalDateTime timestamp;

    public WorkoutSet(Long userId, String exerciseName, Double exerciseWeightKg, Integer exerciseReps) {
        this.userId = userId;
        this.exerciseName = exerciseName;
        this.exerciseWeightKg = exerciseWeightKg;
        this.exerciseReps = exerciseReps;
        this.timestamp = LocalDateTime.now();
    }
}
