package org.app.train7smartapp.exercise.service;

import org.app.train7smartapp.exercise.model.Exercise;
import org.app.train7smartapp.exercise.repository.ExerciseRepository;
//import org.app.train7smartapp.web.dto.importJsonFiles.ExercisesData;

import org.app.train7smartapp.user.model.User;
import org.app.train7smartapp.web.dto.ExerciseRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;

    @Autowired
    public ExerciseService(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    public void createNewExercises(ExerciseRequest exerciseRequest, User user) {

        Exercise exercises = Exercise.builder()
                .name(exerciseRequest.getName())
                .creator(user)
                .category(exerciseRequest.getCategory())
                .description(exerciseRequest.getDescription())
                .muscleGroupsTargeted(exerciseRequest.getMuscleGroupsTargeted())
                .difficulty(exerciseRequest.getDifficulty())
                .equipment(exerciseRequest.getEquipment())
                .steps(exerciseRequest.getSteps())
                .build();

        exerciseRepository.save(exercises);
    }

    public List<Exercise> getAllExercises() {

        return exerciseRepository.findAll();

    }
}
