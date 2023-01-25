package com.saldmy.conferencemanagementrest.controller.advice;

import com.saldmy.conferencemanagementrest.exception.RegistrationNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RegistrationNotFoundAdvice {

    @ExceptionHandler(RegistrationNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String registrationNotFoundHandler(RegistrationNotFoundException e) {
        return e.getMessage();
    }

}
