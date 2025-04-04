package org.app.train7smartapp.calorieCalculator.service;


import org.app.train7smartapp.calorieCalculator.client.CalculatorClient;
import org.app.train7smartapp.calorieCalculator.client.dto.CalorieRecord;
import org.app.train7smartapp.calorieCalculator.client.dto.CalorieRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CalculatorService {

    private final CalculatorClient calculatorClient;

    @Autowired
    public CalculatorService(CalculatorClient calculatorClient) {
        this.calculatorClient = calculatorClient;
    }

    public CalorieRecord sendCalculation(CalorieRequest request) {
        return calculatorClient.calculateAndSave(request).getBody();
    }

    public List<CalorieRecord> getUserCalorieHistory(UUID userId) {
        return calculatorClient.getRecordsByUser(userId).getBody();
    }



}
