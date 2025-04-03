package org.app.train7smartapp.calorieCalculator.client.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CalorieRequest {
    private int age;
    private double weight;
    private double height;
    private String activity;
}
