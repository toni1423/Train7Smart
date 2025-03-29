package org.app.train7smartapp.calorieCalculator.service;

import org.app.train7smartapp.web.dto.UserDataRequest;
import org.springframework.stereotype.Service;

@Service
public class CalorieCalculatorService {

    public double calculateCalories(UserDataRequest userDataRequest) {
        double bmr;

        if ("male".equalsIgnoreCase(userDataRequest.getGender())) {
            bmr = 10 * userDataRequest.getWeight() + 6.25 * userDataRequest.getHeight() - 5 * userDataRequest.getAge() + 5;
        } else {
            bmr = 10 * userDataRequest.getWeight() + 6.25 *userDataRequest.getHeight() - 5 * userDataRequest.getAge() - 161;
        }

        return bmr * getActivityFactor(userDataRequest.getActivityLevel());
    }

    private double getActivityFactor(String activityLevel) {
        return switch (activityLevel.toLowerCase()) {
            case "sedentary" -> 1.2;
            case "light" -> 1.375;
            case "moderate" -> 1.55;
            case "active" -> 1.725;
            case "very active" -> 1.9;
            default -> 1.2;
        };
    }
}
