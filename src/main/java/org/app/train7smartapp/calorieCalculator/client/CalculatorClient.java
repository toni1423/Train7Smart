package org.app.train7smartapp.calorieCalculator.client;

import org.app.train7smartapp.calorieCalculator.client.dto.CalorieRequest;
import org.app.train7smartapp.calorieCalculator.client.dto.CalorieResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "calorieCalculator-svc", url = "http://localhost:8081/api/calories")
public interface CalculatorClient {

    @PostMapping("/calculate")
    CalorieResponse calculateCalories(@RequestBody CalorieRequest request);
}