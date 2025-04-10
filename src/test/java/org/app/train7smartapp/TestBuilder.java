package org.app.train7smartapp;

import lombok.experimental.UtilityClass;
import org.app.train7smartapp.user.model.Country;
import org.app.train7smartapp.user.model.Role;
import org.app.train7smartapp.user.model.User;

import java.time.LocalDateTime;
import java.util.UUID;

@UtilityClass
    public class TestBuilder {

        public static User aRandomUser() {

            User user = User.builder()
                    .id(UUID.randomUUID())
                    .username("User")
                    .password("123123")
                    .role(Role.USER)
                    .country(Country.BULGARIA)
                    .isActive(true)
                    .createdOn(LocalDateTime.now())
                    .updatedOn(LocalDateTime.now())
                    .build();

            return user;
        }
    }

