
package org.app.train7smartapp.web;

import org.app.train7smartapp.calorieCalculator.client.CalculatorClient;
import org.app.train7smartapp.calorieCalculator.client.dto.CalorieCalculation;
import org.app.train7smartapp.calorieCalculator.client.dto.CalorieRequest;
import org.app.train7smartapp.calorieCalculator.service.CalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
public class CalculatorController {

    private final CalculatorService calculatorService;

    private final CalculatorClient calculatorClient;

    @Autowired
    public CalculatorController(CalculatorService calculatorService, CalculatorClient calculatorClient) {
        this.calculatorService = calculatorService;
        this.calculatorClient = calculatorClient;
    }

    @GetMapping("/calories/form/{userId}")
    public String showForm(@PathVariable UUID userId, Model model) {
        CalorieRequest request = new CalorieRequest();
        request.setUserId(userId);
        model.addAttribute("calorieRequest", request);
        return "calorie-form";
    }

    @PostMapping("/calories/submit")
    public String submitForm(@ModelAttribute CalorieRequest request, Model model) {
        CalorieCalculation result = calculatorService.sendCalculation(request);
        model.addAttribute("result", result);
        return "calorie-result";
    }

    @GetMapping("/calories/user/{userId}/history")
    public String getHistoryByUserId(@PathVariable UUID userId, Model model) {
        // Извикваме Feign клиента, за да получим историята на калориите за потребителя
        List<CalorieCalculation> history = calculatorClient.getHistoryByUserId(userId);

        // Добавяме данните към модела за Thymeleaf
        model.addAttribute("history", history);

        // Връщаме името на Thymeleaf шаблона, който ще бъде рендериран
        return "calorie-history";
    }
}