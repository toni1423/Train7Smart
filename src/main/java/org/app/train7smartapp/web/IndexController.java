package org.app.train7smartapp.web;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.app.train7smartapp.security.AuthenticationDetails;
import org.app.train7smartapp.user.model.User;
import org.app.train7smartapp.user.service.UserService;
import org.app.train7smartapp.web.dto.LoginRequest;
import org.app.train7smartapp.web.dto.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

    private final UserService userService;

    @Autowired
    public IndexController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String getIndexPage() {
        return "index";
    }

    @GetMapping("/register")
    public ModelAndView getRegisterPage() {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("register");
        modelAndView.addObject("registerRequest", new RegisterRequest());
        return modelAndView;
    }

    @PostMapping("/register")
    public String registrationDataProcessing(@Valid RegisterRequest registerRequest, BindingResult bindingResult, HttpSession session) {

        if (bindingResult.hasErrors()) {
            return "/register";
        }

        User registeredUser = userService.registerNewUser(registerRequest);




        return "redirect:/login";
    }

    @GetMapping("/login")
    public ModelAndView getLoginPage(@RequestParam(value = "error",  required = false)  String errorParam) {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        modelAndView.addObject("loginRequest", new LoginRequest());
        if (errorParam != null) {
            modelAndView.addObject("errorMessage", "Invalid username or password");
        }

        return modelAndView;
    }


    @GetMapping("/home")
    public ModelAndView getHomePage(@AuthenticationPrincipal AuthenticationDetails authenticationDetails) {

        User user = userService.getById(authenticationDetails.getUserId());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home");
        modelAndView.addObject("user", user);
        return modelAndView;
    }
}
