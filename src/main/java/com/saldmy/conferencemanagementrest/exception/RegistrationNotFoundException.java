package com.saldmy.conferencemanagementrest.exception;

import com.saldmy.conferencemanagementrest.entity.RegistrationId;

public class RegistrationNotFoundException extends RuntimeException {

    public RegistrationNotFoundException(RegistrationId id) {
        super("User '" + id.getUserId() + "' does not participate in conference '" + id.getConferenceId() + "'");
    }

}
