package com.saldmy.conferencemanagementrest.util;

import com.saldmy.conferencemanagementrest.entity.Conference;
import com.saldmy.conferencemanagementrest.entity.ConferenceStatus;
import com.saldmy.conferencemanagementrest.exception.IllegalConferenceStatusException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ConferenceUtils {

    private static final String INCORRECT_START_DATETIME = "start datetime set incorrectly";
    private static final String ALREADY_FINISHED = "could not cancel already finished conference";
    private static final String CANCELLED_CONF = "could not finish cancelled conference";

    public void checkStatus(Conference conference, ConferenceStatus newStatus) {
        final LocalDateTime start = conference.getStart();

        switch (newStatus) {
            case REGISTERED -> {
                if (conference.getStatus() == ConferenceStatus.FINISHED) {
                    throw new IllegalConferenceStatusException(ALREADY_FINISHED);
                }
                if (start.compareTo(LocalDateTime.now()) < 0) {
                    throw new IllegalConferenceStatusException(INCORRECT_START_DATETIME);
                }
            }
            case FINISHED -> {
                if (conference.getStatus() == ConferenceStatus.CANCELLED) {
                    throw new IllegalConferenceStatusException(CANCELLED_CONF);
                }
                if (start.compareTo(LocalDateTime.now()) > 0) {
                    throw new IllegalConferenceStatusException(INCORRECT_START_DATETIME);
                }
            }
            case CANCELLED -> {
                if (conference.getStatus() == ConferenceStatus.FINISHED) {
                    throw new IllegalConferenceStatusException(ALREADY_FINISHED);
                }
            }
        }
    }

}
