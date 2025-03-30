package org.app.train7smartapp.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CalorieRequest {
    private String username;
    private double weight;
    private double height;
    private int age;
    private String gender;
    private int activityLevel;
}
