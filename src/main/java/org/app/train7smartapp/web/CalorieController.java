
package org.app.train7smartapp.web;
import org.app.train7smartapp.calorieCalculator.client.dto.CalorieRecord;
import org.app.train7smartapp.calorieCalculator.client.dto.CalorieRequest;
import org.app.train7smartapp.calorieCalculator.service.CalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CalorieController {

    private final CalculatorService calorieService;

    @Autowired
    public CalorieController(CalculatorService calorieService) {
        this.calorieService = calorieService;
    }

    @GetMapping("/calories/form")
    public String showForm(Model model) {
        model.addAttribute("calorieRequest", new CalorieRequest());
        return "calorie-form"; // Thymeleaf форма
    }

    @PostMapping("/calories/submit")
    public String submitForm(@ModelAttribute CalorieRequest request, Model model) {
        CalorieRecord record = calorieService.sendCalculation(request);
        model.addAttribute("result", record);
        return "calorie-result"; // Thymeleaf резултат
    }

    @GetMapping("/calories/history")
    public String showHistory(Model model) {
        model.addAttribute("records", calorieService.fetchAllRecords());
        return "calorie-history"; // История на заявките
    }
}