package com.myaudiolibrary.web.exception;


import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityNotFoundException;
import javax.persistence.NonUniqueResultException;


@ControllerAdvice
public class GlobalExceptionHandler
{

    @ExceptionHandler(EntityNotFoundException.class)
    public ModelAndView handleEntityNotFoundException(EntityNotFoundException e)
    {
        System.out.println("j'arrive dans l'exception");
        ModelAndView modelAndView = new ModelAndView("pageErreur", HttpStatus.NOT_FOUND);
        modelAndView.addObject("error", e.getMessage()+" EntityNotFoundException");
        modelAndView.addObject("status", HttpStatus.NOT_FOUND);

        return modelAndView;

    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ModelAndView handleIllegalArgumentException(IllegalArgumentException e)
    {

        ModelAndView modelAndView = new ModelAndView("pageErreur", HttpStatus.NOT_FOUND);
        modelAndView.addObject("error", e.getMessage()+" IllegalArgumentException");
        modelAndView.addObject("status", HttpStatus.NOT_FOUND);

        return modelAndView;

    }

    @ExceptionHandler(PropertyReferenceException.class)
    public ModelAndView handlePropertyReferenceException(PropertyReferenceException e)
    {

        ModelAndView modelAndView = new ModelAndView("pageErreur", HttpStatus.NOT_FOUND);
        modelAndView.addObject("error", e.getMessage()+" PropertyReferenceException");
        modelAndView.addObject("status", HttpStatus.NOT_FOUND);

        return modelAndView;

    }


    @ExceptionHandler(NonUniqueResultException.class)
    public ModelAndView handleNonUniqueResultException(NonUniqueResultException e)
    {

        ModelAndView modelAndView = new ModelAndView("pageErreur", HttpStatus.CONFLICT);
        modelAndView.addObject("error", e.getMessage()+" NonUniqueResultException");
        modelAndView.addObject("status", HttpStatus.CONFLICT);

        return modelAndView;

    }


    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ModelAndView handleEmptyResultDataAccessException(EmptyResultDataAccessException e)
    {

        ModelAndView modelAndView = new ModelAndView("pageErreur", HttpStatus.NOT_FOUND);
        modelAndView.addObject("error", e.getMessage()+" EmptyResultDataAccessException");
        modelAndView.addObject("status", HttpStatus.CONFLICT);

        return modelAndView;

    }




}
