package org.app.train7smartapp.web.dto;

import lombok.Builder;
import lombok.Data;
import org.app.train7smartapp.user.model.Country;
import org.app.train7smartapp.user.model.Role;
import org.app.train7smartapp.user.model.User;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
public class UserInformation {
    private UUID id;
    private String username;
    private String email;
    private Role role;
    private Country country;
    private boolean isActive;
    private LocalDateTime createdOn;
}
