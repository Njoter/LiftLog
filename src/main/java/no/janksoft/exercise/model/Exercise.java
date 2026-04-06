package no.janksoft.exercise.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "exercises")
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    private Double weightKg;
    private Integer reps;
    private Integer sets;

    public Exercise(String name, Double weightKg, int reps, int sets) {
        this.name = name;
        this.weightKg = weightKg;
        this.reps = reps;
        this.sets = sets;
    }
}
