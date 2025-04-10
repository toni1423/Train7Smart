package org.app.train7smartapp.unit;

import org.app.train7smartapp.exeption.UsernameAlreadyExistException;
import org.app.train7smartapp.user.model.*;
import org.app.train7smartapp.user.repository.UserRepository;
import org.app.train7smartapp.user.service.UserService;
import org.app.train7smartapp.web.dto.RegisterRequest;
import org.app.train7smartapp.web.dto.UserEditRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private UUID userId;
    private UserEditRequest userEditRequest;
    private User existingUser;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository, passwordEncoder);

        // Подготвяме тестови данни
        userId = UUID.randomUUID();
        userEditRequest = UserEditRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .profilePicture("newProfilePic.jpg")
                .fitnessLevel(FitnessLevel.INTERMEDIATE)
                .height(String.valueOf(180))
                .weight(String.valueOf(75))
                .age(30)
                .city("New York")
                .gender(Gender.MALE)
                .build();

        // Създаваме съществуващ потребител в базата
        existingUser = User.builder()
                .id(userId)
                .firstName("")
                .lastName("")
                .email("old.email@example.com")
                .profilePicture("")
                .fitnessLevel(FitnessLevel.BEGINNER)
                .height(String.valueOf(170))
                .weight(String.valueOf(65))
                .age(25)
                .city("Rome")
                .gender(Gender.FEMALE)
                .build();
    }

    @Test
    public void testUserEditData_updatesUserCorrectly() {
        // Мокваме findById да връща съществуващия потребител
        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));

        // Извикваме метода, който тестваме
        userService.userEditData(userId, userEditRequest);

        // Проверяваме дали данните са актуализирани
        assertEquals("John", existingUser.getFirstName());
        assertEquals("Doe", existingUser.getLastName());
        assertEquals("john.doe@example.com", existingUser.getEmail());
        assertEquals("newProfilePic.jpg", existingUser.getProfilePicture());
        assertEquals("INTERMEDIATE", existingUser.getFitnessLevel().name());
        assertEquals(String.valueOf(180), existingUser.getHeight());
        assertEquals(String.valueOf(75), existingUser.getWeight());
        assertEquals(30, existingUser.getAge());
        assertEquals("New York", existingUser.getCity());
        assertEquals("MALE", existingUser.getGender().name());

        // Проверяваме дали save е извикан точно веднъж с актуализирания потребител
        verify(userRepository, times(1)).save(existingUser);
    }

    @Test
    public void testUserEditData_throwsExceptionWhenUserNotFound() {
        // Мокваме findById да връща празен Optional (потребителят не съществува)
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Проверяваме дали се хвърля изключение, когато потребителят не бъде намерен
        assertThrows(org.app.train7smartapp.exeption.DomainException.class, () -> {
            userService.userEditData(userId, userEditRequest);
        });
    }

    @Test
    public void testRegisterNewUser_successfullyRegistersUser() {
        RegisterRequest registerRequest = RegisterRequest.builder()
                .username("toni123")
                .password("123321")
                .country(Country.BULGARIA)
                .build();

        when(userRepository.findByUsername("toni123")).thenReturn(Optional.empty());

        // Мокваме passwordEncoder
        when(passwordEncoder.encode("123321")).thenReturn("encoded123321");

        User savedUser = new User();
        savedUser.setId(UUID.randomUUID());
        savedUser.setUsername("toni123");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        User result = userService.registerNewUser(registerRequest);

        assertEquals("toni123", result.getUsername());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testRegisterNewUser_throwsWhenUsernameAlreadyExists() {
        // Arrange
        RegisterRequest request = new RegisterRequest();
        request.setUsername("toni_123");

        User existingUser = new User(); // Създаваме реален потребител
        existingUser.setUsername("toni_123");

        when(userRepository.findByUsername("toni_123"))
                .thenReturn(Optional.of(existingUser)); // -> не Optional.of(null)!

        // Act + Assert
        UsernameAlreadyExistException exception = assertThrows(
                UsernameAlreadyExistException.class,
                () -> userService.registerNewUser(request)
        );

        assertTrue(exception.getMessage().contains("toni_123"));
    }

    @Test
    public void testGetAllUsers_returnsListOfUsers() {
        // Подготовка на мокнати потребители
        User ivan = User.builder()
                .id(UUID.randomUUID())
                .username("ivan_21")
                .build();

        User gosho = User.builder()
                .id(UUID.randomUUID())
                .username("gosho.32")
                .build();

        List<User> mockUsers = List.of(ivan, gosho);

        // Мокване на userRepository.findAll()
        when(userRepository.findAll()).thenReturn(mockUsers);

        // Извикване на метода, който тестваме
        List<User> result = userService.getAllUsers();

        // Проверка
        assertEquals(2, result.size());
        assertEquals("ivan_21", result.get(0).getUsername());
        assertEquals("gosho.32", result.get(1).getUsername());

        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void testGetUserById_returnsUser() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));

        User result = userService.getById(userId);

        assertEquals(existingUser.getId(), result.getId());
        assertEquals(existingUser.getUsername(), result.getUsername());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    public void testGetUserById_throwsWhenUserNotFound() {
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(org.app.train7smartapp.exeption.DomainException.class, () -> {
            userService.getById(userId);
        });

        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void userSwitchStatus_shouldToggleStatus() {
        // Потребителят е активен по начало
        existingUser.setActive(true);
        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));

        userService.userSwitchStatus(userId);

        assertFalse(existingUser.isActive(), "\n" +
                "The status should be changed to false.");
        verify(userRepository, times(1)).save(existingUser);
    }

    @Test
    void userSwitchRole_shouldChangeRole() {
        existingUser.setRole(Role.USER);
        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));

        userService.userSwitchRole(userId);

        assertEquals(Role.ADMIN, existingUser.getRole());
        verify(userRepository, times(1)).save(existingUser);
    }

    @Test
    void loadUserByUsername_success() {
        existingUser.setUsername("petko23");
        existingUser.setPassword("encoded123321");
        existingUser.setActive(true);
        existingUser.setRole(Role.USER);

        when(userRepository.findByUsername("petko23")).thenReturn(Optional.of(existingUser));

        var result = userService.loadUserByUsername("petko23");

        assertNotNull(result);
        assertEquals("petko23", result.getUsername());
        assertEquals("encoded123321", result.getPassword());
    }

    @Test
    void loadUserByUsername_shouldThrowIfNotFound() {
        when(userRepository.findByUsername("petko23")).thenReturn(Optional.empty());

        assertThrows(org.app.train7smartapp.exeption.DomainException.class, () -> {
            userService.loadUserByUsername("petko23");
        });
    }
}
