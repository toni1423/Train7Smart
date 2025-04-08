package org.app.train7smartapp.web;

import jakarta.validation.Valid;
import org.app.train7smartapp.security.AuthenticationDetails;
import org.app.train7smartapp.user.model.User;
import org.app.train7smartapp.user.service.UserService;
import org.app.train7smartapp.web.dto.WorkoutProgramRequest;
import org.app.train7smartapp.workoutProgram.model.WorkoutProgram;
import org.app.train7smartapp.workoutProgram.service.WorkoutProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/workouts")
public class WorkoutProgramController {

    private final WorkoutProgramService workoutProgramService;
    private final UserService userService;

    @Autowired
    public WorkoutProgramController(WorkoutProgramService workoutProgramService, UserService userService) {
        this.workoutProgramService = workoutProgramService;
        this.userService = userService;
    }

    @GetMapping("/new")
    public ModelAndView getNewWorkoutPage(@AuthenticationPrincipal AuthenticationDetails authenticationDetails) {

        User user = userService.getById(authenticationDetails.getUserId());

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("new-workout-program");
        modelAndView.addObject("user", user);
        modelAndView.addObject("workoutProgramRequest", new WorkoutProgramRequest());

        return modelAndView;

    }

    @PostMapping
    public ModelAndView createNewWorkout(@Valid WorkoutProgramRequest workoutProgramRequest, BindingResult bindingResult, @AuthenticationPrincipal AuthenticationDetails authenticationDetails) {

        User user = userService.getById(authenticationDetails.getUserId());

        if (bindingResult.hasErrors()) {

            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("new-workout-program");
            modelAndView.addObject("user", user);
            modelAndView.addObject("workoutProgramRequest", workoutProgramRequest);
            return modelAndView;
        }


        workoutProgramService.createNewWorkoutProgram(workoutProgramRequest, user);

        return new ModelAndView("redirect:/home");
    }

    @GetMapping("/history")
    public ModelAndView getWorkoutsLibrary(@AuthenticationPrincipal AuthenticationDetails authenticationDetails) {

        User user = userService.getById(authenticationDetails.getUserId());

        List<WorkoutProgram> workout = workoutProgramService.getAllWorkoutProgram();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("user", user);
        modelAndView.addObject("historyList", workout);
        modelAndView.setViewName("workoutsLibrary");

        return modelAndView;
    }

    @DeleteMapping("/{id}")
    public String deleteWorkoutProgram(@PathVariable UUID id) {
        workoutProgramService.deleteWorkoutProgramById(id);

        // Пренасочване към страницата с всички тренировки (или друга страница)
        return "redirect:/workouts/history";  // или "/workouts", ако искаш да останеш на същата страница
    }

}
