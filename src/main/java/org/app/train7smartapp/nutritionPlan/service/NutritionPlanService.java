package org.app.train7smartapp.nutritionPlan.service;
import org.app.train7smartapp.exeption.DomainException;
import org.app.train7smartapp.nutritionPlan.model.NutritionPlan;
import org.app.train7smartapp.nutritionPlan.repository.NutritionPlanRepository;
import org.app.train7smartapp.user.model.User;
import org.app.train7smartapp.web.dto.NutritionPlanRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class NutritionPlanService {

    private final NutritionPlanRepository nutritionPlanRepository;

    @Autowired
    public NutritionPlanService(NutritionPlanRepository nutritionPlanRepository) {
        this.nutritionPlanRepository = nutritionPlanRepository;
    }


    public void createNewNutritionPlan(NutritionPlanRequest nutritionPlanRequest, User user) {

        NutritionPlan creatednutritionPlan = NutritionPlan.builder()
                .name(nutritionPlanRequest.getName())
                .creator(user)
                .dailyCaloricIntake(String.valueOf(nutritionPlanRequest.getDailyCaloricIntake()))
                .macronutrientBreakdown(nutritionPlanRequest.getMacronutrientBreakdown())
                .dietaryRestrictions(nutritionPlanRequest.getDietaryRestrictions())
                .nutritionPlanUrl(nutritionPlanRequest.getNutritionPlanUrl())
                .build();

        nutritionPlanRepository.save(creatednutritionPlan);
    }

    public List<NutritionPlan> getAllNutritionPlans() {

        return nutritionPlanRepository.findAll();

    }

    public void deleteNutritionPlanById(UUID id) {
        if (!nutritionPlanRepository.existsById(id)) {
            throw new DomainException("Nutrition Plan with id [%s] not found.".formatted(id), HttpStatus.NOT_FOUND);
        }
        nutritionPlanRepository.deleteById(id);
    }

}
