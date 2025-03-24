package org.app.train7smartapp.web.dto;


import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;
import org.app.train7smartapp.user.model.FitnessLevel;
import org.app.train7smartapp.user.model.Gender;
import org.hibernate.validator.constraints.URL;

@Builder
@Data
public class UserEditRequest {


    @NotBlank
    @Size(max = 40, message = "First name must be a maximum of 40 characters!")
    private String firstName;

    @NotBlank
    @Size(max = 40, message = "Last name must be a maximum of 40 characters!")
    private String lastName;

    @Email
    private String email;

    @URL
    private String profilePicture;

    private FitnessLevel fitnessLevel;

    private Gender gender;

    @Positive
    private String height;

    @Positive
    private String weight;

    @Positive
    private int age;

    @NotBlank
    private String city;

}
