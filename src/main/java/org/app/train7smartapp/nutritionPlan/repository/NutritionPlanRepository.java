package org.app.train7smartapp.nutritionPlan.repository;

import org.app.train7smartapp.nutritionPlan.model.NutritionPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface NutritionPlanRepository extends JpaRepository<NutritionPlan, UUID> {
}
