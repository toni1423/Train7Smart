package org.app.train7smartapp.web;

import jakarta.validation.Valid;
import org.app.train7smartapp.calorieCalculator.service.CalorieCalculatorService;
import org.app.train7smartapp.web.dto.UserDataRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/calories")
public class CalorieCalculatorController {

    private final CalorieCalculatorService calorieCalculatorService;

    @Autowired
    public CalorieCalculatorController(CalorieCalculatorService calorieCalculatorService) {
        this.calorieCalculatorService = calorieCalculatorService;
    }

    @GetMapping("/calculator")
    public ModelAndView showCalculator(@AuthenticationPrincipal UserDetails userDetails) {
        ModelAndView modelAndView = new ModelAndView("calculator");
        modelAndView.addObject("userData", new UserDataRequest());
        modelAndView.addObject("username", userDetails.getUsername());  // Взима името на логнатия потребител
        return modelAndView;
    }

    @PostMapping("/calculate")
    public ModelAndView calculateCalories(
            @Valid @ModelAttribute("userData") UserDataRequest userDataRequest,
            BindingResult bindingResult,
            @AuthenticationPrincipal UserDetails userDetails) {

        ModelAndView modelAndView = new ModelAndView("calculator");

        if (bindingResult.hasErrors()) {
            modelAndView.addObject("username", userDetails.getUsername());
            return modelAndView;  // Ако има грешки във формата, остава на същата страница
        }

        double calories = calorieCalculatorService.calculateCalories(userDataRequest);
        modelAndView.addObject("calories", String.format("%.2f", calories));
        modelAndView.addObject("username", userDetails.getUsername());

        return modelAndView;
    }
}
