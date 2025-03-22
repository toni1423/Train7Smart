package org.app.train7smartapp.web;

import jakarta.servlet.http.HttpSession;
import org.app.train7smartapp.security.RequireAdminRole;
import org.app.train7smartapp.user.model.User;
import org.app.train7smartapp.user.repository.UserRepository;
import org.app.train7smartapp.user.service.UserService;
import org.app.train7smartapp.web.dto.UserEditRequest;
import org.app.train7smartapp.web.dto.UserInformation;
import org.app.train7smartapp.web.mapper.DtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping("/{id}/profile")
    public ModelAndView getProfileMenu(@PathVariable UUID id) {

        User user = userService.getById(id);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("profile-menu");
        modelAndView.addObject("user", user);
        modelAndView.addObject("userEditRequest", UserEditRequest.builder().build());

        return modelAndView;
    }

    @PutMapping("/{id}/profile")
    public String updateUserProfile(@PathVariable UUID id, UserEditRequest userEditRequest) {

        userService.userEditData(id, userEditRequest);

        return "redirect:/home";
    }

    @RequireAdminRole
    @GetMapping
    public ModelAndView getAllUsers() {

        List<User> users = userService.getAllUsers();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("users");
        modelAndView.addObject("users", users);

        return modelAndView;

    }

    @PutMapping("/{id}/status")
    public String switchUserProfileStatus(@PathVariable UUID id) {

        userService.userSwitchStatus(id);

        return "redirect:/users";
    }

    @PutMapping("/{id}/role")
    public String switchUserRole(@PathVariable UUID id) {

        userService.userSwitchRole(id);

        return "redirect:/users";
    }
    @DeleteMapping("/{id}/delete-user")
    public String deleteUser(@PathVariable UUID id) {

        userService.deleteUserAccount(id);

        return "redirect:/users";
    }

//    @GetMapping("/forgotPassword")
//    public String forgotPassword() {
//
//        return "forgotPassword";
//    }
//
//    @PostMapping("/forgotPassword")
//    public String forgotPasswordProcess(@ModelAttribute UserEditRequest userEditRequest) {
//
//        User user = userRepository.findByEmail(userEditRequest.getEmail());
//
//        if (user != null) {
//
//        }
//        return "";
//    }
}
