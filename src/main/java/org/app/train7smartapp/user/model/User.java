package org.app.train7smartapp.user.model;

import jakarta.persistence.*;
import lombok.*;
import org.app.train7smartapp.nutritionPlan.model.NutritionPlan;
import org.app.train7smartapp.workoutProgram.model.FavoriteWorkoutProgram;
import org.app.train7smartapp.workoutProgram.model.WorkoutProgram;

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
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Country country;

    @Enumerated(EnumType.STRING)
    private Role role;

    //Additional fields (EditProfile):


    private String firstName;


    private String lastName;

    @Column(unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private FitnessLevel fitnessLevel;

    @Column
    private int age;

    @Column
    private String profilePicture;

    @Column
    private String city;

    @Enumerated(EnumType.STRING)
    private Gender gender;


    private String height;


    private String weight;



    @Column
    private boolean isActive;

    @Column(nullable = false)
    private LocalDateTime createdOn;

    @Column(nullable = false)
    private LocalDateTime updatedOn;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "creator")
    private List<WorkoutProgram> workoutPrograms = new ArrayList<>();

    @OneToMany(mappedBy = "creator", fetch = FetchType.EAGER)
    private List<FavoriteWorkoutProgram> favoriteWorkoutPrograms = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "creator")
    @OrderBy("creator ASC")
    private List<NutritionPlan> nutritionPlans = new ArrayList<>();

}
