package org.app.train7smartapp;

import org.app.train7smartapp.exeption.UsernameAlreadyExistException;
import org.app.train7smartapp.security.AuthenticationDetails;
import org.app.train7smartapp.user.model.Role;
import org.app.train7smartapp.web.IndexController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.UUID;

import static org.app.train7smartapp.TestBuilder.aRandomUser;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(IndexController.class)
public class IndexControllerApiTest {

    // ВАЖНО: Когато тествам контролери трябва да МОКНА всички депендънсита на този контролер с анотация @MockitoBean!!!
    @MockitoBean
    private org.app.train7smartapp.user.service.UserService userService;

    // Използвам MockMvc за да изпращам заявки
    @Autowired
    private MockMvc mockMvc;

    // Send GET /
    // Result - view name index
    @Test
    void getRequestToIndexEndpoint_shouldReturnIndexView() throws Exception {

        // 1. Build Request
        MockHttpServletRequestBuilder request = get("/");

        //.andExpect() - проверявам резултата
        // MockMvcResultMatchers.status() - проверка на статуса
        // 2. Send Request
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    void getRequestToRegisterEndpoint_shouldReturnRegisterView() throws Exception {

        // 1. Build Request
        MockHttpServletRequestBuilder request = get("/register");

        //.andExpect() - проверявам резултата
        // MockMvcResultMatchers.status() - проверка на статуса
        // model().attributeExists("registerRequest") - проверява дали има конкретен NOT NULL атрибут
        // model().attribute("registerRequest", instanceOf(RegisterRequest.class))
        // 2. Send Request
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().attributeExists("registerRequest"));
    }

    @Test
    void getRequestToLoginEndpoint_shouldReturnLoginView() throws Exception {

        // 1. Build Request
        MockHttpServletRequestBuilder request = get("/login");

        // 2. Send Request
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().attributeExists("loginRequest"));
    }

    @Test
    void getRequestToLoginEndpointWithErrorParameter_shouldReturnLoginViewAndErrorMessageAttribute() throws Exception {

        // 1. Build Request
        MockHttpServletRequestBuilder request = get("/login").param("error", "");

        // 2. Send Request
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().attributeExists("loginRequest", "errorMessage"));
    }

    // POST with correct form data
    // Expect:
    // status - 3xx Redirect Status
    // called .register method of userService
    // redirect to /login
    @Test
    void postRequestToRegisterEndpoint_happyPath() throws Exception {

        // 1. Build Request
        MockHttpServletRequestBuilder request = post("/register")
                .formField("username", "toni_123")
                .formField("password", "123456789")
                .formField("country", "BULGARIA")
                .with(csrf());

        // 2. Send Request
        mockMvc.perform(request)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
        verify(userService, times(1)).registerNewUser(any());
    }

    @Test
    void postRequestToRegisterEndpointWithExistingUsername_shouldRedirectToRegisterWithFlashMessage() throws Exception {
        // 1. Мокваме изключението, което ще бъде хвърлено от userService
        when(userService.registerNewUser(any())).thenThrow(new UsernameAlreadyExistException("Username already exists!"));

        // 2. Build Request
        MockHttpServletRequestBuilder request = post("/register")
                .formField("username", "Vik123")
                .formField("password", "123456")
                .formField("country", "BULGARIA")
                .with(csrf());

        // 3. Send Request
        mockMvc.perform(request)
                .andExpect(status().is3xxRedirection())  // Очакваме редирект
                .andExpect(redirectedUrl("/register"))  // Проверяваме, че редиректва към регистрацията
                .andExpect(flash().attributeExists("usernameAlreadyExistMessage"));  // Проверяваме, че flash атрибутът е наличен

        // Уверяваме се, че registerNewUser е извикан точно веднъж
        verify(userService, times(1)).registerNewUser(any());
    }

    @Test
    void postRequestToRegisterEndpointWithInvalidData_returnRegisterView() throws Exception {

        // 1. Build Request
        MockHttpServletRequestBuilder request = post("/register")
                .formField("username", "")
                .formField("password", "")
                .formField("country", "BULGARIA")
                .with(csrf());

        // 2. Send Request
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("register"));
        verify(userService, never()).registerNewUser(any());
    }

    @Test
    void getAuthenticatedRequestToHome_returnsHomeView() throws Exception {

        // 1. Build Request
        when(userService.getById(any())).thenReturn(aRandomUser());

        UUID userId = UUID.randomUUID();
        AuthenticationDetails details = new AuthenticationDetails(userId, "User321", "123321", Role.USER, true);
        MockHttpServletRequestBuilder request = get("/home")
                .with(user(details));

        // 2. Send Request
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect(model().attributeExists("user"));
        verify(userService, times(1)).getById(userId);
    }

    @Test
    void getUnauthenticatedRequestToHome_redirectToLogin() throws Exception {

        // 1. Build Request
        MockHttpServletRequestBuilder request = get("/home");

        // 2. Send Request
        mockMvc.perform(request)
                .andExpect(status().is3xxRedirection());
        verify(userService, never()).getById(any());
    }
}