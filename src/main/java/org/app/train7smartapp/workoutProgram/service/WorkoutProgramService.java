package org.app.train7smartapp.workoutProgram.service;


import org.app.train7smartapp.user.model.User;
import org.app.train7smartapp.web.dto.WorkoutProgramRequest;
import org.app.train7smartapp.workoutProgram.model.WorkoutProgram;
import org.app.train7smartapp.workoutProgram.repository.WorkoutProgramsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class WorkoutProgramService {

    private final WorkoutProgramsRepository workoutProgramsRepository;


    @Autowired
    public WorkoutProgramService(WorkoutProgramsRepository workoutProgramsRepository) {
        this.workoutProgramsRepository = workoutProgramsRepository;
    }

    public void createNewWorkoutProgram(WorkoutProgramRequest workoutProgramRequest, User user) {

        WorkoutProgram workoutProgram = WorkoutProgram.builder()
                .creator(user)
                .name(workoutProgramRequest.getName())
                .duration(workoutProgramRequest.getDuration())
                .level(workoutProgramRequest.getLevel())
                .goal(workoutProgramRequest.getGoal())
                .url(workoutProgramRequest.getUrl())
                .build();

        workoutProgramsRepository.save(workoutProgram);
    }

    public List<WorkoutProgram> getAllWorkoutProgram() {

        return workoutProgramsRepository.findAll();

    }

    public void deleteWorkoutProgramById(UUID id) {

        workoutProgramsRepository.deleteById(id);
    }
}
