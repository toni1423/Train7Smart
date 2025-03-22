package org.app.train7smartapp.workoutProgram.service;

import org.app.train7smartapp.exeption.DomainException;
import org.app.train7smartapp.exercise.model.Exercise;
import org.app.train7smartapp.user.model.User;
//import org.app.train7smartapp.web.dto.WorkoutProgramRequest;
import org.app.train7smartapp.web.dto.ExerciseRequest;
import org.app.train7smartapp.web.dto.WorkoutProgramRequest;
import org.app.train7smartapp.workoutProgram.model.FavoriteWorkoutProgram;
import org.app.train7smartapp.workoutProgram.model.WorkoutProgram;
import org.app.train7smartapp.workoutProgram.repository.FavoriteWorkoutProgramsRepository;
import org.app.train7smartapp.workoutProgram.repository.WorkoutProgramsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class WorkoutProgramService {

    private final WorkoutProgramsRepository workoutProgramsRepository;
    private final FavoriteWorkoutProgramsRepository favoriteWorkoutProgramsRepository;

    @Autowired
    public WorkoutProgramService(WorkoutProgramsRepository workoutProgramsRepository, FavoriteWorkoutProgramsRepository favoriteWorkoutProgramsRepository) {
        this.workoutProgramsRepository = workoutProgramsRepository;
        this.favoriteWorkoutProgramsRepository = favoriteWorkoutProgramsRepository;
    }

    public void createNewWorkoutProgram(WorkoutProgramRequest workoutProgramRequest, User user) {

        WorkoutProgram workoutProgram = WorkoutProgram.builder()
                .creator(user)
                .name(workoutProgramRequest.getName())
                .duration(workoutProgramRequest.getDuration())
                .level(workoutProgramRequest.getLevel())
                .goal(workoutProgramRequest.getGoal())
                .createdOn(LocalDateTime.now())
                .url(workoutProgramRequest.getUrl())
                .build();

        workoutProgramsRepository.save(workoutProgram);
    }

    public List<WorkoutProgram> getAllWorkoutProgram() {

        return workoutProgramsRepository.findAll();

    }


    /*public void createNewWorkoutProgram(WorkoutProgramRequest workoutProgramRequest, User user) {

        Optional<WorkoutProgram> optionalWorkout = workoutProgramsRepository.findByName(workoutProgramRequest.getName());

        if (optionalWorkout.isPresent()) {
            throw  new DomainException("Workout with name=[%s] already exist.".formatted(workoutProgramRequest.getName()), HttpStatus.BAD_REQUEST);
        }

        WorkoutProgram creatingWorkoutProgram = WorkoutProgram.builder()
                .id(UUID.randomUUID())
                .name(workoutProgramRequest.getName())
                .goal(workoutProgramRequest.getGoal())
                .duration(workoutProgramRequest.getDuration())
                .level(workoutProgramRequest.getLevel())
                .createdOn(LocalDateTime.now())
                .url(workoutProgramRequest.getUrl())
                .build();

        workoutProgramsRepository.save(creatingWorkoutProgram);

    }

    public void deleteWorkoutProgram(UUID workoutId) {
        workoutProgramsRepository.deleteById(workoutId);
    }

    public List<WorkoutProgram> getAllWorkoutPrograms(UUID workoutId) {

        List<WorkoutProgram> allWorkoutPrograms = workoutProgramsRepository.findAll();

        allWorkoutPrograms.sort(Comparator.comparing(WorkoutProgram::getName).reversed().thenComparing(WorkoutProgram::getLevel));

        return allWorkoutPrograms;
    }

    public void createFavouriteWorkoutProgramById(UUID workoutId, User user) {

        WorkoutProgram workoutProgram = getWorkoutProgramById(workoutId);

        boolean isAlreadyFavourite = user.getFavoriteWorkoutPrograms()
                .stream()
                .anyMatch(w -> w.getName().equals(workoutProgram.getName()) && w.getLevel().equals(workoutProgram.getLevel()));
        if (isAlreadyFavourite){
            return;
        }

        FavoriteWorkoutProgram favoriteWorkoutProgram = FavoriteWorkoutProgram.builder()
                .id(UUID.randomUUID())
                .creator(workoutProgram.getCreator())
                .name(workoutProgram.getName())
                .duration(workoutProgram.getDuration())
                .level(workoutProgram.getLevel())
                .goal(workoutProgram.getGoal())
                .createdOn(LocalDateTime.now())
                .url(workoutProgram.getUrl())
                .build();

        favoriteWorkoutProgramsRepository.save(favoriteWorkoutProgram);

    }

    private WorkoutProgram getWorkoutProgramById(UUID workoutId) {

        return workoutProgramsRepository.findById(workoutId).orElseThrow(()
               -> new RuntimeException("Workout with id %s does not exist".formatted(workoutId)));

    }

    public void deleteFavoriteWorkoutProgramById(UUID id) {

        favoriteWorkoutProgramsRepository.deleteById(id);

    }*/
}
