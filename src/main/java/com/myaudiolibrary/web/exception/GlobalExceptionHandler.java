package com.myaudiolibrary.web.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityNotFoundException;


@ControllerAdvice
public class GlobalExceptionHandler
{

    @ExceptionHandler(EntityNotFoundException.class)
    public ModelAndView handleEntityNotFoundException(EntityNotFoundException e)
    {
        System.out.println("j'arrive dans l'exception");
        ModelAndView modelAndView = new ModelAndView("pageErreur", HttpStatus.NOT_FOUND);
        modelAndView.addObject("error", e.getMessage());
        modelAndView.addObject("status", HttpStatus.NOT_FOUND);

        return modelAndView;

    }


}
