package org.app.train7smartapp.calorieCalculator.service;


import org.app.train7smartapp.calorieCalculator.client.CalculatorClient;
import org.app.train7smartapp.calorieCalculator.client.dto.CalorieRequest;
import org.app.train7smartapp.calorieCalculator.client.dto.CalorieResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CalculatorService {

    private final CalculatorClient calorieClient;

    @Autowired
    public CalculatorService(CalculatorClient calorieClient) {
        this.calorieClient = calorieClient;
    }

    public CalorieResponse calculateCalories(CalorieRequest request) {
        return calorieClient.calculateCalories(request);
    }

}
