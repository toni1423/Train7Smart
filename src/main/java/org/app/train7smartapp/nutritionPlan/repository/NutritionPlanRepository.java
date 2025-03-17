package org.app.train7smartapp.nutritionPlan.repository;

import org.app.train7smartapp.nutritionPlan.model.NutritionPlan;
import org.app.train7smartapp.workoutProgram.model.WorkoutProgram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface NutritionPlanRepository extends JpaRepository<NutritionPlan, UUID> {
    Optional<NutritionPlan> findByName(String name);
}
