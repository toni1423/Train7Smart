package org.app.train7smartapp;

import org.app.train7smartapp.user.model.User;
import org.app.train7smartapp.workoutProgram.model.Goal;
import org.app.train7smartapp.workoutProgram.model.Level;
import org.app.train7smartapp.workoutProgram.model.WorkoutProgram;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WorkoutControllerTest {
    @Mock
    private User mockUser;

    @InjectMocks
    private WorkoutProgram workoutProgram;

    @Test
    void testWorkoutProgramCreation() {
        when(mockUser.getId()).thenReturn(UUID.randomUUID());

        workoutProgram = WorkoutProgram.builder()
                .id(UUID.randomUUID())
                .creator(mockUser)
                .name("Full Body Strength")
                .duration("8 weeks")
                .level(Level.INTERMEDIATE)
                .goal(Goal.MUSCLE_GAIN)
                .url("http://example.com/workout")
                .build();

        assertThat(workoutProgram).isNotNull();
        assertThat(workoutProgram.getName()).isEqualTo("Full Body Strength");
        assertThat(workoutProgram.getDuration()).isEqualTo("8 weeks");
        assertThat(workoutProgram.getLevel()).isEqualTo(Level.INTERMEDIATE);
        assertThat(workoutProgram.getGoal()).isEqualTo(Goal.MUSCLE_GAIN);
        assertThat(workoutProgram.getUrl()).isEqualTo("http://example.com/workout");
    }
}
