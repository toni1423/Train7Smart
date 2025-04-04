package org.app.train7smartapp.calorieCalculator.client;

import org.app.train7smartapp.calorieCalculator.client.dto.CalorieRecord;
import org.app.train7smartapp.calorieCalculator.client.dto.CalorieRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "calorieCalculator-svc", url = "http://localhost:8081/api/calories")
public interface CalculatorClient {



    @PostMapping("/calculate")
    ResponseEntity<CalorieRecord> calculateAndSave(@RequestBody CalorieRequest request);

    @GetMapping("/records")
    ResponseEntity<List<CalorieRecord>> getAllRecords();

    @GetMapping("/records/{id}")
    ResponseEntity<CalorieRecord> getRecordById(@PathVariable("id") Long id);
}