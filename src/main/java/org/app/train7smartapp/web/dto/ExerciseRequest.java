package org.app.train7smartapp.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.app.train7smartapp.exercise.model.Category;
import org.app.train7smartapp.exercise.model.Diffculty;
import org.app.train7smartapp.exercise.model.MuscleGroupsTargeted;
import org.app.train7smartapp.user.model.User;
import org.hibernate.validator.constraints.URL;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExerciseRequest {

    @NotBlank(message = "Name cannot be empty")
    @Size(max = 30, message = "Name must be a maximum of 30 characters!")
    private String name;

    private User creator;

    private Category category;

    @Size(max = 1000, message = "Description must be a maximum of 1000 characters")
    private String description;

    private MuscleGroupsTargeted muscleGroupsTargeted;

    private Diffculty difficulty;

    @Size(max = 50, message = "Description must be a maximum of 50 characters")
    private String equipment;

    @Size(max = 1000, message = "Description must be a maximum of 1000 characters")
    private String steps;

    @URL
    private String videoURL;

}
