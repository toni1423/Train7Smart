
package org.app.train7smartapp.web;
import org.app.train7smartapp.calorieCalculator.client.CalculatorClient;
import org.app.train7smartapp.calorieCalculator.client.dto.CalorieRequest;
import org.app.train7smartapp.calorieCalculator.client.dto.CalorieResponse;
import org.app.train7smartapp.calorieCalculator.service.CalculatorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CalorieController {

    private final CalculatorService calorieService;

    public CalorieController(CalculatorService calorieService) {
        this.calorieService = calorieService;
    }

    @PostMapping("/calculate")
    public String calculateCalories(@ModelAttribute CalorieRequest request, Model model) {
        CalorieResponse response = calorieService.calculateCalories(request);
        model.addAttribute("calories", response.getCalories());
        return "result";
    }
}