package org.app.train7smartapp.web;


import jakarta.validation.Valid;
import org.app.train7smartapp.nutritionPlan.model.NutritionPlan;
import org.app.train7smartapp.nutritionPlan.service.NutritionPlanService;
import org.app.train7smartapp.security.AuthenticationDetails;
import org.app.train7smartapp.user.model.User;
import org.app.train7smartapp.user.service.UserService;
import org.app.train7smartapp.web.dto.NutritionPlanRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/nutritionPlans")
public class NutritionPlanController {

    private final NutritionPlanService nutritionPlanService;
    private final UserService userService;

    @Autowired
    public NutritionPlanController(NutritionPlanService nutritionPlanService, UserService userService) {
        this.nutritionPlanService = nutritionPlanService;
        this.userService = userService;
    }


    @GetMapping("/new")
    public ModelAndView getNewNutritionPlan(@AuthenticationPrincipal AuthenticationDetails authenticationDetails) {

        User user = userService.getById(authenticationDetails.getUserId());

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("new-nutritionPlan");
        modelAndView.addObject("user", user);
        modelAndView.addObject("nutritionPlanRequest", new NutritionPlanRequest());

        return modelAndView;
    }

    @PostMapping
    public ModelAndView createNewNutritionPlan(@Valid NutritionPlanRequest nutritionPlanRequest, BindingResult bindingResult, @AuthenticationPrincipal AuthenticationDetails authenticationDetails) {

        User user = userService.getById(authenticationDetails.getUserId());

        if (bindingResult.hasErrors()) {

            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("new-nutritionPlan");
            modelAndView.addObject("user", user);

            return modelAndView;
        }

        nutritionPlanService.createNewNutritionPlan(nutritionPlanRequest, user);

        return new ModelAndView("redirect:/home");
    }


    @GetMapping
    public ModelAndView getAllNutritionPlans(@AuthenticationPrincipal AuthenticationDetails authenticationDetails) {

        User user = userService.getById(authenticationDetails.getUserId());

        List<NutritionPlan> nutritionPlanLibrary = nutritionPlanService.getAllNutritionPlans();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("nutritionPlanLibrary");
        modelAndView.addObject("nutritionPlans", nutritionPlanLibrary);
        modelAndView.addObject("user", user);

        return modelAndView;

    }
}
