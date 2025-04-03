//package org.app.train7smartapp.calorieCalculator.service;
//
//import org.app.train7smartapp.calorieCalculator.model.UserData;
//import org.app.train7smartapp.calorieCalculator.repository.UserDataRepository;
//import org.app.train7smartapp.exeption.DomainException;
//import org.app.train7smartapp.user.model.User;
//import org.app.train7smartapp.user.repository.UserRepository;
//import org.app.train7smartapp.web.dto.UserDataRequest;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.UUID;
//
//@Service
//public class CalorieCalculatorService {
//
//    private final UserDataRepository userDataRepository;
//    UserRepository userRepository;
//
//    @Autowired
//    public CalorieCalculatorService(UserDataRepository userDataRepository, UserRepository userRepository) {
//        this.userDataRepository = userDataRepository;
//        this.userRepository = userRepository;
//    }
//
//    public double calculateCalories(UserDataRequest userDataRequest) {
//        double bmr;
//
//        if ("male".equalsIgnoreCase(userDataRequest.getGender())) {
//            bmr = 88.36 + (13.4 * userDataRequest.getWeight()) + (4.8 * userDataRequest.getHeight()) - (5.7 * userDataRequest.getAge());
//        } else {
//            bmr = 447.6 + (9.2 * userDataRequest.getWeight()) + (3.1 * userDataRequest.getHeight()) - (4.3 * userDataRequest.getAge());
//        }
//
//        double activityMultiplier = switch (userDataRequest.getActivityLevel()) {
//            case "light" -> 1.375;
//            case "moderate" -> 1.55;
//            case "active" -> 1.725;
//            case "very active" -> 1.9;
//            default -> 1.2;
//        };
//
//        return bmr * activityMultiplier;
//    }
//
//    public UserData saveUserData(UserDataRequest userDataRequest, String username) {
//        double dailyCalories = calculateCalories(userDataRequest);
//
//        UserData userData = new UserData();
//        userData.setUsername(username);
//        userData.setGender(userDataRequest.getGender());
//        userData.setWeight(userDataRequest.getWeight());
//        userData.setHeight(userDataRequest.getHeight());
//        userData.setAge(userDataRequest.getAge());
//        userData.setActivityLevel(userDataRequest.getActivityLevel());
//        userData.setDailyCalories(dailyCalories);
//
//        return userDataRepository.save(userData);
//
//    }
//
//    public List<UserData> getUserHistory(String username) {
//        return userDataRepository.findByUsername(username);
//    }
//}
