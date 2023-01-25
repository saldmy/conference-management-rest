package com.saldmy.conferencemanagementrest.controller.advice;

import com.saldmy.conferencemanagementrest.exception.UserDoesNotParticipateException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserDoesNotParticipateAdvice {

    @ExceptionHandler(UserDoesNotParticipateException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String userDoesNotParticipateHandler(UserDoesNotParticipateException e) {
        return e.getMessage();
    }

}
