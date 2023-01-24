package com.saldmy.conferencemanagementrest;

public class ConferenceNotFoundException extends RuntimeException {

    public ConferenceNotFoundException(Long id) {
        super("Could not find conference " + id);
    }

}
