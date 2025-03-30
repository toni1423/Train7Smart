package org.app.train7smartapp.web;

import org.app.train7smartapp.calorieCalculator.service.CalorieServiceClient;
import org.app.train7smartapp.web.dto.CalorieRequest;
import org.app.train7smartapp.web.dto.CalorieResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/calories")
public class CalorieController {

    private final CalorieServiceClient calorieServiceClient;

    @Autowired
    public CalorieController(CalorieServiceClient calorieServiceClient) {
        this.calorieServiceClient = calorieServiceClient;
    }

    @PostMapping("/calculate")
    public String calculateCalories(@ModelAttribute CalorieRequest request, Model model) {
        CalorieResponse response = calorieServiceClient.calculateCalories(request);
        model.addAttribute("calories", response.getCalories());
        return "calorie-result";
    }
}
