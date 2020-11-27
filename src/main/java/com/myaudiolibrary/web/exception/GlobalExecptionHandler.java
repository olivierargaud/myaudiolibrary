package com.myaudiolibrary.web.exception;



import org.hibernate.PropertyValueException;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;


@RestControllerAdvice
public class GlobalExecptionHandler
{


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//                                            EntityNotFoundException                                                 //
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleEntityNotFoundException(EntityNotFoundException entityNotFoundException)
    {
        return entityNotFoundException.getMessage();
    }


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//                                            MethodArgumentTypeMismatchException                                     //
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleMethodArgumentTypeMismatchException
    (
        MethodArgumentTypeMismatchException methodArgumentTypeMismatchException
    )
    {
        return "le parametre "+methodArgumentTypeMismatchException.getName()
                +" a une valeur incorect : "+methodArgumentTypeMismatchException.getValue();
    }


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//                                            IllegalArgumentException                                                //
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleIllegalArgumentException(IllegalArgumentException illegalArgumentException )
    {
        return illegalArgumentException.getMessage();
    }


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//                                            PropertyReferenceException                                              //
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    @ExceptionHandler(PropertyReferenceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handlePropertyReferenceException(PropertyReferenceException propertyReferenceException)
    {
        return "la propriete "+propertyReferenceException.getPropertyName()+" n'existe pas ";
    }



////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//                                            EntityExistsException                                                   //
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    @ExceptionHandler(EntityExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleEntityExistsException(EntityExistsException entityExistsException)
    {
        return entityExistsException.getMessage();
    }


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//                                            PropertyValueException                                                  //
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    @ExceptionHandler(PropertyValueException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handlePropertyValueException(PropertyValueException propertyValueException )
    {
        return propertyValueException.getMessage();
    }



}
