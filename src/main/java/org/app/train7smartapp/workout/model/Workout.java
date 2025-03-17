package org.app.train7smartapp.workout.model;

import jakarta.persistence.*;
import lombok.*;
import org.app.train7smartapp.exercise.model.Exercise;
import org.app.train7smartapp.workoutProgram.model.WorkoutProgram;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Workout {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne()
    private WorkoutProgram workoutProgram;



    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private int durationInMinutes;

    @Enumerated(EnumType.STRING)
    private IntensityLevel intensityLevel;

    @Enumerated(EnumType.STRING)
    private Type type;





    @OneToMany(fetch = FetchType.EAGER, mappedBy = "workout")
    @OrderBy("name ASC")
    private List<Exercise> exercises= new ArrayList<>();


}
