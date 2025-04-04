package org.app.train7smartapp.calorieCalculator.client.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Data
public class CalorieRequest {

    @NotNull
    private UUID userId;

    @NotNull
    private double weight;

    @NotNull
    private double height;

    @NotNull
    private int age;

    @NotNull
    private String gender;

    @NotNull
    @NotBlank
    private String activityLevel;
}
