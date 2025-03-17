package org.app.train7smartapp.workoutProgram.repository;

import org.app.train7smartapp.workoutProgram.model.FavoriteWorkoutProgram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FavoriteWorkoutProgramsRepository extends JpaRepository<FavoriteWorkoutProgram, UUID> {

}
