package org.app.train7smartapp.calorieCalculator.client;

import com.fasterxml.jackson.databind.util.Converter;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public abstract class UuidConverter implements Converter<String, UUID> {

    @Override
    public UUID convert(String source) {
        try {
            return UUID.fromString(source);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Невалиден UUID: " + source);
        }
    }
}
