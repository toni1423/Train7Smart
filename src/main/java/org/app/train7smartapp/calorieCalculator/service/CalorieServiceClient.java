package org.app.train7smartapp.calorieCalculator.service;

import org.app.train7smartapp.web.dto.CalorieRequest;
import org.app.train7smartapp.web.dto.CalorieResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class CalorieServiceClient {
    private final WebClient webClient;

    public CalorieServiceClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8082/api/calories").build();
    }

    public CalorieResponse calculateCalories(CalorieRequest request) {
        return webClient.post()
                .uri("/calculate")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(CalorieResponse.class)
                .block();
    }
}
