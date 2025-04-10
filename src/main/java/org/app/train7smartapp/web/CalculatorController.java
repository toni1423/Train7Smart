
package org.app.train7smartapp.web;

import jakarta.validation.Valid;
import org.app.train7smartapp.calorieCalculator.client.CalculatorClient;
import org.app.train7smartapp.calorieCalculator.client.dto.CalorieCalculation;
import org.app.train7smartapp.calorieCalculator.client.dto.CalorieRequest;
import org.app.train7smartapp.calorieCalculator.service.CalculatorService;
import org.app.train7smartapp.security.AuthenticationDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CalculatorController {

    private final CalculatorService calculatorService;

    private final CalculatorClient calculatorClient;

    @Autowired
    public CalculatorController(CalculatorService calculatorService, CalculatorClient calculatorClient) {
        this.calculatorService = calculatorService;
        this.calculatorClient = calculatorClient;
    }


    // Вместо да използваш userId от URL, използвай данни от @AuthenticationPrincipal
    @GetMapping("/calories/form")
    public String showForm(@AuthenticationPrincipal AuthenticationDetails authenticationDetails, Model model) {
        CalorieRequest request = new CalorieRequest();
        request.setUserId(authenticationDetails.getUserId());
        model.addAttribute("calorieRequest", request);
        return "calorie-form";  // Това трябва да е името на Thymeleaf шаблона
    }

    // Добавяме валидация за формата и изпращаме данните за калориите
    @PostMapping("/calories/submit")
    public String submitForm(@Valid @ModelAttribute CalorieRequest request, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "calorie-form"; // Ако има грешки, връщаме потребителя към формата
        }
        // Извършваме калкулацията
        CalorieCalculation result = calculatorService.sendCalculation(request);
        model.addAttribute("result", result);
        return "calorie-result";
    }

    // Вземаме историята на калориите за текущия потребител чрез @AuthenticationPrincipal
    @GetMapping("/calories/user/history")
    public String getHistoryByUserId(@AuthenticationPrincipal AuthenticationDetails authenticationDetails, Model model) {
        // Извикваме Feign клиента, за да получим историята на калориите за потребителя
        List<CalorieCalculation> history = calculatorClient.getHistoryByUserId(authenticationDetails.getUserId());

        // Добавяме данните към модела за Thymeleaf
        model.addAttribute("history", history);

        // Връщаме името на Thymeleaf шаблона, който ще бъде рендериран
        return "calorie-history";
    }
}

//    @GetMapping("/calories/form/{userId}")
//    public String showForm(@PathVariable UUID userId, Model model) {
//        CalorieRequest request = new CalorieRequest();
//        request.setUserId(userId);
//        model.addAttribute("calorieRequest", request);
//        return "calorie-form";
//    }
//
//    @PostMapping("/calories/submit")
//    public String submitForm(@ModelAttribute CalorieRequest request, Model model) {
//        CalorieCalculation result = calculatorService.sendCalculation(request);
//        model.addAttribute("result", result);
//        return "calorie-result";
//    }
//
//    @GetMapping("/calories/user/{userId}/history")
//    public String getHistoryByUserId(@PathVariable UUID userId, Model model) {
//        // Извикваме Feign клиента, за да получим историята на калориите за потребителя
//        List<CalorieCalculation> history = calculatorClient.getHistoryByUserId(userId);
//
//        // Добавяме данните към модела за Thymeleaf
//        model.addAttribute("history", history);
//
//        // Връщаме името на Thymeleaf шаблона, който ще бъде рендериран
//        return "calorie-history";
//    }
