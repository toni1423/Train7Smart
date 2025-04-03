package org.app.train7smartapp.user.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.app.train7smartapp.exeption.DomainException;
import org.app.train7smartapp.security.AuthenticationDetails;
import org.app.train7smartapp.user.model.FitnessLevel;
import org.app.train7smartapp.user.model.Role;
import org.app.train7smartapp.user.model.User;
import org.app.train7smartapp.user.repository.UserRepository;
import org.app.train7smartapp.web.dto.RegisterRequest;
import org.app.train7smartapp.web.dto.UserEditRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class UserService implements UserDetailsService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Transactional
    public User registerNewUser(RegisterRequest registerRequest) {

        Optional<User> optionalUser = userRepository.findByUsername(registerRequest.getUsername());
        if (optionalUser.isPresent()) {
            throw new DomainException("User with username=[%s] already exist.".formatted(registerRequest.getUsername()), HttpStatus.BAD_REQUEST);
        }

        User user = userRepository.save(initializeUser(registerRequest));

        log.info("Successfully create new user account for username [%s] and id [%s]".formatted(user.getUsername(), user.getId()));

        return user;

    }

    public void userEditData(UUID userID, UserEditRequest editRequest) {

        User user = getById(userID);

        user.setFirstName(editRequest.getFirstName());
        user.setLastName(editRequest.getLastName());
        user.setEmail(editRequest.getEmail());
        user.setProfilePicture(editRequest.getProfilePicture());
        user.setFitnessLevel(editRequest.getFitnessLevel());
        user.setHeight(editRequest.getHeight());
        user.setWeight(editRequest.getWeight());
        user.setAge(editRequest.getAge());
        user.setCity(editRequest.getCity());
        user.setGender(editRequest.getGender());

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
                .password(passwordEncoder.encode(registerRequest.getPassword()))
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

    public void deleteUserAccountById(UUID id) {

        User user = getById(id);

        userRepository.deleteById(id);

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username).orElseThrow(() -> new DomainException("User with username=[%s] does not exist. ".formatted(username), HttpStatus.BAD_REQUEST));

        return new AuthenticationDetails(user.getId(), user.getUsername(), user.getPassword(), user.getRole(), user.isActive());

    }
}
