package org.app.train7smartapp.web;

import org.app.train7smartapp.exeption.DomainException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler({AuthorizationDeniedException.class,
                       DomainException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView handleAccessDeniedException() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("error-not-found"); // Това е името на Thymeleaf шаблона (403.html)
        mav.addObject("errorTitle", "Oops! We couldn't find that.");
        mav.addObject("errorMessage", "If the problem persists, please contact support.");
        return mav;
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView handleMethodArgumentTypeMismatchException() {
        ModelAndView mav = new ModelAndView("error-not-found");
        mav.addObject("errorTitle", "Oops! We couldn't find that.");
        mav.addObject("errorMessage", "If the problem persists, please contact support.");
        return mav;
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView handleDataIntegrityViolationException() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("error-not-found"); // Това е името на Thymeleaf шаблона (403.html)
        mav.addObject("errorTitle", "The email you are trying to enter already exists in the system. Please use a different one.");
        return mav;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ModelAndView handleAnyException(Exception exception) {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("internal-server-error");
        modelAndView.addObject("errorMessage", exception.getClass().getSimpleName());

        return modelAndView;
    }
}