package org.app.train7smartapp.web;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.app.train7smartapp.user.model.User;
import org.app.train7smartapp.user.service.UserService;
import org.app.train7smartapp.web.dto.WorkoutProgramRequest;
import org.app.train7smartapp.workoutProgram.service.WorkoutProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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
    public ModelAndView getNewWorkoutPage(HttpSession session) {

        UUID userId = (UUID) session.getAttribute("user_id");
        User user = userService.getById(userId);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("new-workout-program");
        modelAndView.addObject("user", user);
        modelAndView.addObject("workoutProgramRequest", new WorkoutProgramRequest());

        return modelAndView;

    }

    @PostMapping
    public ModelAndView createNewWorkout(@Valid WorkoutProgramRequest workoutProgramRequest, BindingResult bindingResult, HttpSession session) {

        UUID userId = (UUID) session.getAttribute("user_id");
        User user = userService.getById(userId);

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

//    @DeleteMapping("/{id}")
//    public String deleteWorkout(@PathVariable UUID id) {
//
//        workoutProgramService.deleteWorkoutProgram(id);
//
//        return "redirect:/home";
//    }
//
//    @PostMapping("/favourites/{id}")
//    public String makeFavouriteWorkout(@PathVariable UUID id, HttpSession session) {
//
//        UUID userId = (UUID) session.getAttribute("user_id");
//        User user = userService.getById(userId);
//
//        workoutProgramService.createFavouriteWorkoutProgramById(id, user);
//
//        return "redirect:/home";
//    }
//
//
//    @DeleteMapping("/favourites/{id}")
//    public String deleteFavouriteWorkout(@PathVariable UUID id) {
//
//        workoutProgramService.deleteFavoriteWorkoutProgramById(id);
//
//        return "redirect:/home";
//    }

}
