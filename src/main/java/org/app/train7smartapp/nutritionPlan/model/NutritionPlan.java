package org.app.train7smartapp.nutritionPlan.model;

import jakarta.persistence.*;
import lombok.*;
import org.app.train7smartapp.user.model.User;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class NutritionPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    private User creator;

    private String name;


    private String dailyCaloricIntake;

    @Enumerated(EnumType.STRING)
    private MacronutrientBreakdown macronutrientBreakdown;

    @Enumerated(EnumType.STRING)
    private DietaryRestrictions dietaryRestrictions;

    @Column(nullable = false)
    private String nutritionPlanUrl;


    //Meal Suggestions (List of meals)

}
