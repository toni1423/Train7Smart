package org.app.train7smartapp.exeption;

public class CalculatorServiceFeignCallException extends RuntimeException {

    public CalculatorServiceFeignCallException() {
    }

    public CalculatorServiceFeignCallException(String message) {
        super(message);
    }
}
