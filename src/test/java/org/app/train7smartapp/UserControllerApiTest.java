package org.app.train7smartapp;

import org.app.train7smartapp.security.AuthenticationDetails;
import org.app.train7smartapp.user.model.Role;
import org.app.train7smartapp.web.UserController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerApiTest {

    @MockitoBean
    private org.app.train7smartapp.user.service.UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void putUnauthorizedRequestToSwitchRole_shouldReturn302AndRedirectToUsers() throws Exception {

        // 1. Създаване на Request
        AuthenticationDetails principal = new AuthenticationDetails(UUID.randomUUID(), "User123", "123123", Role.USER, true);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put("/users/{id}/role", UUID.randomUUID())
                .with(SecurityMockMvcRequestPostProcessors.user(principal))  // Използване на user() от SecurityMockMvcRequestPostProcessors
                .with(csrf());

        // 2. Изпращане на Request и проверка на резултата
        mockMvc.perform(request)
                .andExpect(status().is3xxRedirection())  // Очакваме пренасочване
                .andExpect(redirectedUrl("/users"));  // Очакваме да се пренасочи към /users
    }

    @Test
    void putAuthorizedRequestToSwitchRole_shouldRedirectToUsers() throws Exception {

        // 1. Build Request
        AuthenticationDetails principal = new AuthenticationDetails(UUID.randomUUID(), "User123", "123123", Role.ADMIN, true);
        MockHttpServletRequestBuilder request = put("/users/{id}/role", UUID.randomUUID())
                .with(user(principal))
                .with(csrf());

        // 2. Send Request
        mockMvc.perform(request)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users"));
        verify(userService, times(1)).userSwitchRole(any());
    }
}
