package org.app.train7smartapp.user.service;

import jakarta.transaction.Transactional;
import lombok.Builder;
import org.app.train7smartapp.exeption.DomainException;
import org.app.train7smartapp.user.model.FitnessLevel;
import org.app.train7smartapp.user.model.Role;
import org.app.train7smartapp.user.model.User;
import org.app.train7smartapp.user.repository.UserRepository;
import org.app.train7smartapp.web.dto.LoginRequest;
import org.app.train7smartapp.web.dto.RegisterRequest;
import org.app.train7smartapp.web.dto.UserEditRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class UserService {


    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Transactional
    public User registerNewUser(RegisterRequest registerRequest) {

        Optional<User> optionalUser = userRepository.findByUsername(registerRequest.getUsername());
        if (optionalUser.isPresent()) {
            throw new DomainException("User with username=[%s] already exist.".formatted(registerRequest.getUsername()), HttpStatus.BAD_REQUEST);
        }

        return userRepository.save(initializeUser(registerRequest));

    }

    public User loginUser(LoginRequest loginRequest) {

        Optional<User> optionalUser = userRepository.findByUsername(loginRequest.getUsername());

        if (optionalUser.isEmpty()) {
            throw new DomainException("User with username=[%s] does not exist.".formatted(loginRequest.getUsername()), HttpStatus.BAD_REQUEST);
        }

        User user = optionalUser.get();

        if (!user.getPassword().equals(loginRequest.getPassword())) {
            throw new DomainException("Login attempt with incorrect password for user with id [%s].".formatted(user.getId()), HttpStatus.BAD_REQUEST);
        }

        return user;
    }

    public void userEditData(UUID userID, UserEditRequest editRequest) {

        User user = getById(userID);

        user.setFirstName(editRequest.getFirstName());
        user.setLastName(editRequest.getLastName());
        user.setEmail(editRequest.getEmail());
        user.setProfilePicture(editRequest.getProfilePicture());
        user.setFitnessLevel(editRequest.getFitnessLevel());
        user.setGender(editRequest.getGender());
        user.setHeight(editRequest.getHeight());
        user.setWeight(editRequest.getWeight());
        user.setAge(editRequest.getAge());
        user.setCity(editRequest.getCity());

        userRepository.save(user);

    }


    public User getById(UUID userId) {

        return userRepository
                .findById(userId)
                .orElseThrow(() -> new DomainException("User with id [%s] does not exist.".formatted(userId), HttpStatus.BAD_REQUEST));

    }

    public List<User> getAllUsers() {

        return userRepository.findAll();

    }

    private User initializeUser(RegisterRequest registerRequest) {

        return User.builder()
                .username(registerRequest.getUsername())
                .password(registerRequest.getPassword())
//                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(Role.USER)
                .isActive(true)
                .fitnessLevel(FitnessLevel.BEGINNER)
                .country(registerRequest.getCountry())
                .createdOn(LocalDateTime.now())
                .updatedOn(LocalDateTime.now())
                .build();
    }

    public void userSwitchStatus(UUID id) {

        User user = getById(id);

        user.setActive(!user.isActive());
        userRepository.save(user);

    }

    public void userSwitchRole(UUID id) {

        User user = getById(id);

        if (user.getRole() == Role.USER) {
            user.setRole(Role.ADMIN);
        } else {
            user.setRole(Role.USER);
        }

        userRepository.save(user);
    }

    public void deleteUserAccount(UUID id) {

        User user = getById(id);

        userRepository.delete(user);

    }
}
