package org.app.train7smartapp.web.dto;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.app.train7smartapp.workoutProgram.model.Goal;
import org.app.train7smartapp.workout.model.Type;
import org.app.train7smartapp.workoutProgram.model.Level;
import org.hibernate.validator.constraints.URL;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkoutProgramRequest {

    @NotBlank(message = "Name cannot be empty")
    @Size(min = 6, max = 20, message = "Name must be at least 6 symbols!")
    private String name;

    private Goal goal;

    @NotNull
    private String duration;


    private Level level;

    @URL
    private String url;



}

