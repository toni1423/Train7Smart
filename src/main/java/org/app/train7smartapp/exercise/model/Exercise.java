package org.app.train7smartapp.exercise.model;

import jakarta.persistence.*;
import lombok.*;
import org.app.train7smartapp.user.model.User;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "exercise")
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    private User creator;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    private MuscleGroupsTargeted muscleGroupsTargeted;

    @Enumerated(EnumType.STRING)
    private Diffculty difficulty;

    private String equipment;

    @Column(nullable = false)
    private String steps;

    private String videoURL;

}
