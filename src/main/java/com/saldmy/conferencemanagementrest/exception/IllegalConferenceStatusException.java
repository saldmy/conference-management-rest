package com.saldmy.conferencemanagementrest.exception;

public class IllegalConferenceStatusException extends RuntimeException {

    public IllegalConferenceStatusException(String reason) {
        super("Cannot change conference status: " + reason);
    }

}
