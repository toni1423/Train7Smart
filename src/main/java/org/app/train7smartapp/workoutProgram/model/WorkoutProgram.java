package org.app.train7smartapp.workoutProgram.model;

import jakarta.persistence.*;
import lombok.*;
import org.app.train7smartapp.exercise.model.Exercise;
import org.app.train7smartapp.user.model.User;
import org.app.train7smartapp.workout.model.Workout;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class WorkoutProgram {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    private User creator;




    @Column
    private String name;

    @Column
    private String duration;

    @Enumerated(EnumType.STRING)
    private Level level;


    @Enumerated(EnumType.STRING)
    private Goal goal;

    @Column(nullable = false)
    private LocalDateTime createdOn;


    @Column(nullable = false)
    private String url;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "workoutProgram")
    @OrderBy("name ASC")
    private List<Workout> workouts= new ArrayList<>();

}
