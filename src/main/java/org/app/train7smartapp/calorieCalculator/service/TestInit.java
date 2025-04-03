//package org.app.train7smartapp.calorieCalculator.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Component;
//
//@Component
//public class TestInit implements ApplicationRunner {
//
//    private final CalculatorClient calculatorClient;
//
//    @Autowired
//    public TestInit(CalculatorClient calculatorClient) {
//        this.calculatorClient = calculatorClient;
//    }
//
//
//    @Override
//    public void run(ApplicationArguments args) throws Exception {
//        ResponseEntity<String> response = calculatorClient.getHelloWorld("Toni");
//
//        System.out.println(response.getBody());
//    }
//}
