//package org.app.train7smartapp;
//
//import org.app.train7smartapp.exercise.model.Category;
//import org.app.train7smartapp.exercise.model.Diffculty;
//import org.app.train7smartapp.exercise.model.Exercise;
//import org.app.train7smartapp.exercise.repository.ExerciseRepository;
//import org.app.train7smartapp.exercise.service.ExerciseService;
//import org.app.train7smartapp.user.model.User;
//import org.app.train7smartapp.web.dto.ExerciseRequest;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.ArgumentCaptor;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.util.List;
//
//import static jdk.internal.org.objectweb.asm.util.CheckClassAdapter.verify;
//import static org.mockito.Mockito.times;
//
//public class ExerciseTest {
//
//    @Mock
//    private ExerciseRepository exerciseRepository;
//
//    @InjectMocks
//    private ExerciseService exerciseService;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//        exerciseService = new ExerciseService(exerciseRepository);
//    }
//
//    @Test
//    public void testCreateNewExercise_savesExerciseCorrectly() {
//        // Arrange: създаваме данни
//        User user = User.builder().id(java.util.UUID.randomUUID()).username("test_user").build();
//
//        ExerciseRequest request = ExerciseRequest.builder()
//                .name("Push Up")
//                .category(Category.Lower_Body)
//                .description("Push up exercise")
//                .muscleGroupsTargeted(List.of("Chest", "Triceps"))
//                .difficulty(Diffculty.Medium)
//                .equipment("None")
//                .steps(List.of("Get into position", "Lower body", "Push up"))
//                .videoURL("http://example.com/pushup")
//                .build();
//
//        // Act
//        exerciseService.createNewExercise(request, user);
//
//        // Assert
//        ArgumentCaptor<Exercise> exerciseCaptor = ArgumentCaptor.forClass(Exercise.class);
//        verify(exerciseRepository, times(1)).save(exerciseCaptor.capture());
//
//        Exercise savedExercise = exerciseCaptor.getValue();
//        assert savedExercise.getName().equals("Push Up");
//        assert savedExercise.getCreator().equals(user);
//        assert savedExercise.getCategory() == Category.Lower_Body;
//        assert savedExercise.getDescription().equals("Push up exercise");
//        assert savedExercise.getMuscleGroupsTargeted().contains("Chest");
//        assert savedExercise.getDifficulty() == Diffculty.Medium;
//        assert savedExercise.getEquipment().equals("None");
//        assert savedExercise.getSteps().size() == 3;
//        assert savedExercise.getVideoURL().equals("http://example.com/pushup");
//    }
//}
//}
