package org.app.train7smartapp.calorieCalculator.service;


import org.app.train7smartapp.calorieCalculator.client.CalculatorClient;
import org.app.train7smartapp.calorieCalculator.client.dto.CalorieRecord;
import org.app.train7smartapp.calorieCalculator.client.dto.CalorieRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<CalorieRecord> fetchAllRecords() {
        return calculatorClient.getAllRecords().getBody();
    }

    public CalorieRecord fetchById(Long id) {
        return calculatorClient.getRecordById(id).getBody();
    }

}
