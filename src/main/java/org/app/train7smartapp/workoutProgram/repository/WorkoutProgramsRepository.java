package org.app.train7smartapp.workoutProgram.repository;

import org.app.train7smartapp.workoutProgram.model.WorkoutProgram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface WorkoutProgramsRepository extends JpaRepository<WorkoutProgram, UUID> {

    Optional<WorkoutProgram> findByName(String name);

}
