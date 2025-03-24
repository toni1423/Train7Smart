package org.app.train7smartapp.workoutProgram.model;

import jakarta.persistence.*;
import lombok.*;
import org.app.train7smartapp.user.model.User;
//import org.app.train7smartapp.workout.model.Type;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class FavoriteWorkoutProgram {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    private User creator;



    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String duration;

    @Enumerated(EnumType.STRING)
    private Level level;

    @Enumerated(EnumType.STRING)
    private Goal goal;

    @Column(nullable = false)
    private String url;

}
