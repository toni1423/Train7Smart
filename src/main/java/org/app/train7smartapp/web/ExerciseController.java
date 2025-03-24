package org.app.train7smartapp.web;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.app.train7smartapp.exercise.model.Exercise;
import org.app.train7smartapp.exercise.service.ExerciseService;
import org.app.train7smartapp.user.model.User;
import org.app.train7smartapp.user.service.UserService;
import org.app.train7smartapp.web.dto.ExerciseRequest;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ModelAndView getNewExercise(HttpSession session) {

        UUID userId = (UUID) session.getAttribute("user_id");
        User user = userService.getById(userId);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("new-exercise");
        modelAndView.addObject("user", user);
        modelAndView.addObject("exerciseRequest", new ExerciseRequest());

        return modelAndView;
    }

    @PostMapping
    public ModelAndView createNewExercise(@Valid ExerciseRequest exerciseRequest, BindingResult bindingResult, HttpSession session) {

        UUID userId = (UUID) session.getAttribute("user_id");
        User user = userService.getById(userId);

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
    public ModelAndView getExerciseLibrary(HttpSession session) {

        UUID userId = (UUID) session.getAttribute("user_id");
        User user = userService.getById(userId);

        List<Exercise> exercisesLibrary = exerciseService.getAllExercises();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("user", user);
        modelAndView.addObject("historyList", exercisesLibrary);
        modelAndView.setViewName("exercisesLibrary");

        return modelAndView;
    }

}
