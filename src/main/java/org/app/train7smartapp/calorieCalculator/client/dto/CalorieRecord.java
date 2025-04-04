package org.app.train7smartapp.calorieCalculator.client.dto;

import lombok.*;

import java.util.UUID;

@Data
public class CalorieRecord {
    private UUID id;

    private UUID userId;

    private double weight;

    private double height;

    private int age;

    private String gender;

    private String activityLevel;

    private double calories;
}
