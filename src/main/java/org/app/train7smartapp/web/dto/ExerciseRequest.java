package org.app.train7smartapp.web.dto;


import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.app.train7smartapp.exercise.model.Category;
import org.app.train7smartapp.exercise.model.Diffculty;
import org.app.train7smartapp.exercise.model.MuscleGroupsTargeted;
import org.app.train7smartapp.user.model.User;
import org.attoparser.dom.Text;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExerciseRequest {

    private String name;

    private User creator;

    private Category category;

    private String description;

    private MuscleGroupsTargeted muscleGroupsTargeted;

    private Diffculty difficulty;

    private String equipment;

    private String steps;

}
