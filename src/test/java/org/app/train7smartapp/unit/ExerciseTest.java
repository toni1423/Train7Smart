package org.app.train7smartapp.unit;

import org.app.train7smartapp.exercise.model.Category;
import org.app.train7smartapp.exercise.model.Diffculty;
import org.app.train7smartapp.exercise.model.Exercise;
import org.app.train7smartapp.exercise.model.MuscleGroupsTargeted;
import org.app.train7smartapp.exercise.repository.ExerciseRepository;
import org.app.train7smartapp.exercise.service.ExerciseService;
import org.app.train7smartapp.user.model.User;
import org.app.train7smartapp.web.dto.ExerciseRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ExerciseTest {

    @Mock
    private ExerciseRepository exerciseRepository;

    @InjectMocks
    private ExerciseService exerciseService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        exerciseRepository = mock(ExerciseRepository.class);
        exerciseService = new ExerciseService(exerciseRepository);
    }

    @Test
    public void testCreateNewExercise_savesExerciseCorrectly() {

        User user = User.builder().id(java.util.UUID.randomUUID()).username("ivan.22").build();

        ExerciseRequest request = ExerciseRequest.builder()
                .name("Push Up")
                .category(Category.Lower_Body)
                .description("Push up exercise")
                .muscleGroupsTargeted(MuscleGroupsTargeted.CHEST)
                .difficulty(Diffculty.Medium)
                .equipment("None")
                .videoURL("https://www.youtube.com/watch?v=IODxDxX7oi4")
                .build();


        exerciseService.createNewExercise(request, user);


        ArgumentCaptor<Exercise> exerciseCaptor = ArgumentCaptor.forClass(Exercise.class);
        verify(exerciseRepository, times(1)).save(exerciseCaptor.capture());

        Exercise savedExercise = exerciseCaptor.getValue();
        assertAll("Exercise Fields",
                () -> assertEquals("Push Up", savedExercise.getName()),
                () -> assertEquals(user, savedExercise.getCreator()),
                () -> assertEquals(Category.Lower_Body, savedExercise.getCategory()),
                () -> assertEquals("Push up exercise", savedExercise.getDescription()),
                () -> assertEquals(Diffculty.Medium, savedExercise.getDifficulty()),
                () -> assertEquals("None", savedExercise.getEquipment()),
                () -> assertEquals("https://www.youtube.com/watch?v=IODxDxX7oi4", savedExercise.getVideoURL()));

    }

    @Test
    public void testGetAllExercises() {

        Exercise exercise1 = new Exercise();
        Exercise exercise2 = new Exercise();
        List<Exercise> mockList = Arrays.asList(exercise1, exercise2);

        when(exerciseRepository.findAll()).thenReturn(mockList);


        List<Exercise> result = exerciseService.getAllExercises();


        assertEquals(2, result.size());
        verify(exerciseRepository, times(1)).findAll();
    }

    @Test
    public void testDeleteExerciseById() {

        UUID id = UUID.randomUUID();


        exerciseService.deleteExerciseById(id);


        verify(exerciseRepository, times(1)).deleteById(id);
    }

    @Test
    public void testDeleteExerciseByIdThrowsException() {

        UUID id = UUID.randomUUID();


        doThrow(new EmptyResultDataAccessException(1))
                .when(exerciseRepository).deleteById(id);


        try {
            exerciseService.deleteExerciseById(id);
        } catch (EmptyResultDataAccessException e) {

            assertEquals(1, e.getExpectedSize());
        }

        verify(exerciseRepository, times(1)).deleteById(id);
    }
}
