package org.app.train7smartapp;

import org.app.train7smartapp.nutritionPlan.model.DietaryRestrictions;
import org.app.train7smartapp.nutritionPlan.model.MacronutrientBreakdown;
import org.app.train7smartapp.nutritionPlan.model.NutritionPlan;
import org.app.train7smartapp.nutritionPlan.repository.NutritionPlanRepository;
import org.app.train7smartapp.nutritionPlan.service.NutritionPlanService;
import org.app.train7smartapp.user.model.User;
import org.app.train7smartapp.web.dto.NutritionPlanRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class NutritionPlanServiceTest {

    @Mock
    private NutritionPlanRepository nutritionPlanRepository;

    @InjectMocks
    private NutritionPlanService nutritionPlanService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        nutritionPlanService = new NutritionPlanService(nutritionPlanRepository);
    }

    @Test
    public void testCreateNewNutritionPlan_savesNutritionPlanCorrectly() {
        // Arrange: подготвяме данни
        User creator = User.builder()
                .id(UUID.randomUUID())
                .username("ivan-23")
                .build();

        NutritionPlanRequest request = NutritionPlanRequest.builder()
                .name("High Protein Plan")
                .dailyCaloricIntake(2200)
                .macronutrientBreakdown(MacronutrientBreakdown.PROTEINS)
                .dietaryRestrictions(DietaryRestrictions.GLUTEN_INTOLERANCE)
                .nutritionPlanUrl("https://fitnesudoma.aktivenizdrav.com/?_ga=2.250863878.1001732588.1744128740-1793201762.1744128740")
                .build();

        // Act
        nutritionPlanService.createNewNutritionPlan(request, creator);

        // Assert
        ArgumentCaptor<NutritionPlan> planCaptor = ArgumentCaptor.forClass(NutritionPlan.class);
        verify(nutritionPlanRepository, times(1)).save(planCaptor.capture());

        NutritionPlan savedPlan = planCaptor.getValue();
        assertEquals("High Protein Plan", savedPlan.getName());
        assertEquals("2200", savedPlan.getDailyCaloricIntake());
        assertEquals(MacronutrientBreakdown.PROTEINS, savedPlan.getMacronutrientBreakdown());
        assertEquals(DietaryRestrictions.GLUTEN_INTOLERANCE, savedPlan.getDietaryRestrictions());
        assertEquals("https://fitnesudoma.aktivenizdrav.com/?_ga=2.250863878.1001732588.1744128740-1793201762.1744128740", savedPlan.getNutritionPlanUrl());
        assertEquals(creator, savedPlan.getCreator());
    }

    @Test
    void getAllNutritionPlans_shouldReturnListOfPlans() {
        // Arrange
        NutritionPlan plan1 = NutritionPlan.builder().name("Plan A").build();
        NutritionPlan plan2 = NutritionPlan.builder().name("Plan B").build();
        List<NutritionPlan> mockPlans = List.of(plan1, plan2);

        when(nutritionPlanRepository.findAll()).thenReturn(mockPlans);

        // Act
        List<NutritionPlan> result = nutritionPlanService.getAllNutritionPlans();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Plan A", result.get(0).getName());
        assertEquals("Plan B", result.get(1).getName());
        verify(nutritionPlanRepository, times(1)).findAll();
    }

    @Test
    void getAllNutritionPlans_shouldReturnEmptyListWhenNoPlansExist() {
        // Arrange
        when(nutritionPlanRepository.findAll()).thenReturn(Collections.emptyList());

        // Act
        List<NutritionPlan> result = nutritionPlanService.getAllNutritionPlans();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(nutritionPlanRepository, times(1)).findAll();
    }
}
