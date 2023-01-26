package com.saldmy.conferencemanagementrest.service;

import com.saldmy.conferencemanagementrest.entity.Conference;
import com.saldmy.conferencemanagementrest.entity.ConferenceStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ConferenceService {

    Conference add(Conference newConference);

    List<Conference> findAll();

    Optional<Conference> find(Long id);

    void delete(Long id);

    Conference update(Long id, Conference newConference);

    Conference changeStatus(Long id, ConferenceStatus newStatus);

}
