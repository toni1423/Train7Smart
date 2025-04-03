package org.app.train7smartapp.web;

import org.app.train7smartapp.user.model.User;
import org.app.train7smartapp.user.service.UserService;
import org.app.train7smartapp.web.dto.UserEditRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
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

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
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

        userService.deleteUserAccountById(id);

        return "redirect:/users";

    }
}
