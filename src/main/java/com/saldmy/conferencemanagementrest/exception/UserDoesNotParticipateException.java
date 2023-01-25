package com.saldmy.conferencemanagementrest.exception;

public class UserDoesNotParticipateException extends RuntimeException {

    public UserDoesNotParticipateException(Long userId, Long conferenceId) {
        super("User '" + userId + "' does not participate in conference '" + conferenceId + "'");
    }

}
