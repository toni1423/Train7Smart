package org.app.train7smartapp.calorieCalculator.service;


import org.app.train7smartapp.calorieCalculator.client.CalculatorClient;
import org.app.train7smartapp.calorieCalculator.client.dto.CalorieCalculation;
import org.app.train7smartapp.calorieCalculator.client.dto.CalorieRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CalculatorService {

    private final CalculatorClient calculatorClient;

    @Autowired
    public CalculatorService(CalculatorClient calculatorClient) {
        this.calculatorClient = calculatorClient;
    }

    public CalorieCalculation sendCalculation(CalorieRequest request) {
        return calculatorClient.calculateAndSave(request).getBody();
    }

}
