package org.app.train7smartapp.unit;

import org.app.train7smartapp.user.model.User;
import org.app.train7smartapp.web.dto.WorkoutProgramRequest;
import org.app.train7smartapp.workoutProgram.model.Goal;
import org.app.train7smartapp.workoutProgram.model.Level;
import org.app.train7smartapp.workoutProgram.model.WorkoutProgram;
import org.app.train7smartapp.workoutProgram.repository.WorkoutProgramsRepository;
import org.app.train7smartapp.workoutProgram.service.WorkoutProgramService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WorkoutProgramServiceTest {

    @Mock
    private WorkoutProgramsRepository workoutProgramsRepository;

    @InjectMocks
    private WorkoutProgramService workoutProgramService;

    @Test
    public void createNewWorkoutProgram_shouldSaveWorkoutProgram() {

        WorkoutProgramRequest request = new WorkoutProgramRequest();
        request.setName("Push-Pull-Legs");
        request.setDuration("6 weeks");
        request.setLevel(Level.INTERMEDIATE);
        request.setGoal(Goal.MUSCLE_GAIN);
        request.setUrl("https://example.com/ppl");

        User user = new User();
        user.setId(UUID.randomUUID());
        user.setUsername("ivan-23");


        workoutProgramService.createNewWorkoutProgram(request, user);


        ArgumentCaptor<WorkoutProgram> captor = ArgumentCaptor.forClass(WorkoutProgram.class);
        verify(workoutProgramsRepository).save(captor.capture());

        WorkoutProgram savedProgram = captor.getValue();
        assertEquals("Push-Pull-Legs", savedProgram.getName());
        assertEquals("6 weeks", savedProgram.getDuration());
        assertEquals(Level.INTERMEDIATE, savedProgram.getLevel());
        assertEquals(Goal.MUSCLE_GAIN, savedProgram.getGoal());
        assertEquals("https://example.com/ppl", savedProgram.getUrl());
        assertEquals(user, savedProgram.getCreator());
    }

    @Test
    public void getAllWorkoutProgram_shouldReturnWorkoutPrograms() {

        WorkoutProgram workoutProgram1 = new WorkoutProgram();
        workoutProgram1.setName("Push-Pull-Legs");

        WorkoutProgram workoutProgram2 = new WorkoutProgram();
        workoutProgram2.setName("Full Body Workout");

        List<WorkoutProgram> workoutPrograms = Arrays.asList(workoutProgram1, workoutProgram2);


        when(workoutProgramsRepository.findAll()).thenReturn(workoutPrograms);


        List<WorkoutProgram> result = workoutProgramService.getAllWorkoutProgram();


        assertEquals(2, result.size());
        assertEquals("Push-Pull-Legs", result.get(0).getName());
        assertEquals("Full Body Workout", result.get(1).getName());
    }

    @Test
    public void getAllWorkoutProgram_shouldReturnEmptyList() {

        when(workoutProgramsRepository.findAll()).thenReturn(Collections.emptyList());


        List<WorkoutProgram> result = workoutProgramService.getAllWorkoutProgram();


        assertEquals(0, result.size());
    }

    @Test
    public void deleteWorkoutProgramById_shouldDeleteProgramSuccessfully_whenProgramExists() {
        UUID workoutProgramId = UUID.randomUUID();


        doNothing().when(workoutProgramsRepository).deleteById(workoutProgramId);


        workoutProgramService.deleteWorkoutProgramById(workoutProgramId);


        verify(workoutProgramsRepository).deleteById(workoutProgramId);
    }

    @Test
    public void deleteWorkoutProgramById_shouldThrowException_whenProgramDoesNotExist() {
        UUID workoutProgramId = UUID.randomUUID();


        doThrow(new IllegalArgumentException("Workout Program not found")).when(workoutProgramsRepository).deleteById(workoutProgramId);


        assertThrows(IllegalArgumentException.class, () -> {
            workoutProgramService.deleteWorkoutProgramById(workoutProgramId);
        });


        verify(workoutProgramsRepository).deleteById(workoutProgramId);
    }
}