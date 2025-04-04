
package org.app.train7smartapp.web;
import org.app.train7smartapp.calorieCalculator.client.dto.CalorieRecord;
import org.app.train7smartapp.calorieCalculator.client.dto.CalorieRequest;
import org.app.train7smartapp.calorieCalculator.service.CalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@Controller
public class CalorieController {

    private final CalculatorService calorieService;

    @Autowired
    public CalorieController(CalculatorService calorieService) {
        this.calorieService = calorieService;
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
        CalorieRecord result = calorieService.sendCalculation(request);
        model.addAttribute("result", result);
        return "calorie-result";
    }

    @GetMapping("/calories/user/{userId}/history")
    public String userHistory(@PathVariable UUID userId, Model model) {
        List<CalorieRecord> records = calorieService.getUserCalorieHistory(userId);
        model.addAttribute("records", records);
        return "calorie-history";
    }
}