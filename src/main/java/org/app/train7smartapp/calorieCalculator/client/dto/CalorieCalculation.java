package org.app.train7smartapp.calorieCalculator.client.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class CalorieCalculation {

    @NotNull
    private UUID userId;

    @NotNull
    private double weight;

    @NotNull
    private double height;

    @NotNull
    private int age;

    private double calories;

    @NotNull
    private String gender;

    @NotNull
    @NotBlank
    private String activityLevel;

    private LocalDateTime calculatedAt;

}
