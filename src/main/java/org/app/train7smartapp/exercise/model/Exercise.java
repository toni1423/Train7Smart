package org.app.train7smartapp.exercise.model;

import jakarta.persistence.*;
import lombok.*;
import org.app.train7smartapp.workout.model.Workout;

import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    private Workout workout;




    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    private Type type;

    @Enumerated(EnumType.STRING)
    private MuscleGroupsTargeted muscleGroupsTargeted;

    @Column
    private String videoURL;


}
