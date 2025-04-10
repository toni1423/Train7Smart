package org.app.train7smartapp.exercise.service;

import org.app.train7smartapp.exercise.model.Exercise;
import org.app.train7smartapp.exercise.repository.ExerciseRepository;

import org.app.train7smartapp.user.model.User;
import org.app.train7smartapp.web.dto.ExerciseRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;

    @Autowired
    public ExerciseService(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    public void createNewExercise(ExerciseRequest exerciseRequest, User user) {

        Exercise exercise = Exercise.builder()
                .name(exerciseRequest.getName())
                .creator(user)
                .category(exerciseRequest.getCategory())
                .description(exerciseRequest.getDescription())
                .muscleGroupsTargeted(exerciseRequest.getMuscleGroupsTargeted())
                .difficulty(exerciseRequest.getDifficulty())
                .equipment(exerciseRequest.getEquipment())
                .videoURL(exerciseRequest.getVideoURL())
                .build();

        exerciseRepository.save(exercise);
    }

    public List<Exercise> getAllExercises() {

        return exerciseRepository.findAll();

    }

    public void deleteExerciseById(UUID id) {

        exerciseRepository.deleteById(id);
    }
}
