//package org.app.train7smartapp.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.ModelAndView;

//public class CalculatorExceptions {
//    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ModelAndView handleCalculatorFeignCallException() {
//        ModelAndView mav = new ModelAndView("error-not-found");
//        mav.addObject("errorTitle", "Oops! We couldn't find that.");
//        mav.addObject("errorStatus", "404 MethodArgumentTypeMismatchException.");
//        return mav;
//    }
//}
