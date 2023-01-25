package com.saldmy.conferencemanagementrest.controller.advice;

import com.saldmy.conferencemanagementrest.exception.IllegalConferenceStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class IllegalConferenceStatusAdvice {

    @ExceptionHandler(IllegalConferenceStatusException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    String illegalConferenceStatusHandler(IllegalConferenceStatusException e) {
        return e.getMessage();
    }

}
