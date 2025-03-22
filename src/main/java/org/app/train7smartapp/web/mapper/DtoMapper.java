package org.app.train7smartapp.web.mapper;

import lombok.experimental.UtilityClass;
import org.app.train7smartapp.exercise.model.Exercise;
import org.app.train7smartapp.user.model.User;
import org.app.train7smartapp.web.dto.ExerciseRequest;
import org.app.train7smartapp.web.dto.UserInformation;

@UtilityClass
public class DtoMapper {

    public static UserInformation toUserInformation(User user) {

        return UserInformation.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .country(user.getCountry())
                .isActive(user.isActive())
                .createdOn(user.getCreatedOn())
                .build();
    }

//    public static ExerciseRequest toExerciseRequest(Exercise exercise, User user) {
//
//        return ExerciseRequest.builder()
//                .creator(user)
//                .name(exercise.getName())
//                .category(exercise.getCategory())
//                .description(exercise.getDescription())
//                .muscleGroupsTargeted(exercise.getMuscleGroupsTargeted())
//                .difficulty(exercise.getDifficulty())
//                .equipment(exercise.getEquipment())
//                .steps(exercise.getSteps())
//                .build();
//    }
}
