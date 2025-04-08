package org.app.train7smartapp.web;

import jakarta.validation.Valid;
import org.app.train7smartapp.exercise.model.Exercise;
import org.app.train7smartapp.exercise.service.ExerciseService;
import org.app.train7smartapp.security.AuthenticationDetails;
import org.app.train7smartapp.user.model.User;
import org.app.train7smartapp.user.service.UserService;
import org.app.train7smartapp.web.dto.ExerciseRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/exercises")
public class ExerciseController {

    private final ExerciseService exerciseService;
    private final UserService userService;

    @Autowired
    public ExerciseController(ExerciseService exerciseService, UserService userService) {
        this.exerciseService = exerciseService;
        this.userService = userService;
    }


    @GetMapping("/new")
    public ModelAndView getNewExercise(@AuthenticationPrincipal AuthenticationDetails authenticationDetails) {

        User user = userService.getById(authenticationDetails.getUserId());

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("new-exercise");
        modelAndView.addObject("user", user);
        modelAndView.addObject("exerciseRequest", new ExerciseRequest());

        return modelAndView;
    }

    @PostMapping
    public ModelAndView createNewExercise(@Valid ExerciseRequest exerciseRequest, BindingResult bindingResult, @AuthenticationPrincipal AuthenticationDetails authenticationDetails) {

        User user = userService.getById(authenticationDetails.getUserId());

        if (bindingResult.hasErrors()) {

            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("new-exercise");
            modelAndView.addObject("user", user);

            return modelAndView;
        }

        exerciseService.createNewExercise(exerciseRequest, user);

        return new ModelAndView("redirect:/home");
    }

    @GetMapping("/history")
    public ModelAndView getExerciseLibrary(@AuthenticationPrincipal AuthenticationDetails authenticationDetails) {

        User user = userService.getById(authenticationDetails.getUserId());

        List<Exercise> exercisesLibrary = exerciseService.getAllExercises();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("user", user);
        modelAndView.addObject("historyList", exercisesLibrary);
        modelAndView.setViewName("exercisesLibrary");

        return modelAndView;
    }

    @DeleteMapping("/{id}")
    public String deleteNutritionPlan(@PathVariable UUID id) {

        exerciseService.deleteExerciseById(id);

        return "redirect:/exercises/history";
    }
}
