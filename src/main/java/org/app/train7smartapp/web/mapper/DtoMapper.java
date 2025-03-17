package org.app.train7smartapp.web.mapper;

import lombok.experimental.UtilityClass;
import org.app.train7smartapp.user.model.User;
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
}
