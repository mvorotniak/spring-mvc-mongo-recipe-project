package com.mvoro.developer.springmvcrecipeproject.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.mvoro.developer.springmvcrecipeproject.exceptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;

/**
 * Global Controller Exception Handler
 */
@Slf4j
@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFoundException(final Exception e) {
        log.error("Handling 'Not Found' Exception...");

        final ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("recipe/errors/404error");
        modelAndView.addObject("exception", e);

        return modelAndView;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NumberFormatException.class)
    public ModelAndView handleNumberFormatException(final Exception e) {
        log.error("Handling 'Bad Request' Exception...");

        final ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("recipe/errors/400error");
        modelAndView.addObject("exception", e);

        return modelAndView;
    }

}
