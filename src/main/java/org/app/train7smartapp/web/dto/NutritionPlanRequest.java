package org.app.train7smartapp.web.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.app.train7smartapp.nutritionPlan.model.DietaryRestrictions;
import org.app.train7smartapp.nutritionPlan.model.MacronutrientBreakdown;
import org.app.train7smartapp.user.model.User;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NutritionPlanRequest {

    private String name;


    private String dailyCaloricIntake;


    private MacronutrientBreakdown macronutrientBreakdown;


    private DietaryRestrictions dietaryRestrictions;

}


//Row was updated or deleted by another transaction (or unsaved-value mapping was incorrect)