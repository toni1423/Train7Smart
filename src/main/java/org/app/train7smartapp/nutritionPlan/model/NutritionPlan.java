package org.app.train7smartapp.nutritionPlan.model;

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
public class NutritionPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    private User creator;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String dailyCaloricIntake;

    @Enumerated(EnumType.STRING)
    private MacronutrientBreakdown macronutrientBreakdown;

    @Enumerated(EnumType.STRING)
    private DietaryRestrictions dietaryRestrictions;

    @Column(nullable = false)
    private String nutritionPlanUrl;

}
