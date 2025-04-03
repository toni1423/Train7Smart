package org.app.train7smartapp.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.app.train7smartapp.nutritionPlan.model.DietaryRestrictions;
import org.app.train7smartapp.nutritionPlan.model.MacronutrientBreakdown;
import org.hibernate.validator.constraints.URL;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NutritionPlanRequest {

    @NotBlank(message = "Name cannot be empty")
    @Size(max = 30, message = "Name must be a maximum of 30 characters!")
    private String name;

    @Positive
    private int dailyCaloricIntake;

    private MacronutrientBreakdown macronutrientBreakdown;

    private DietaryRestrictions dietaryRestrictions;

    @URL
    private String nutritionPlanUrl;

}


