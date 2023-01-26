package com.saldmy.conferencemanagementrest.service.impl;

import com.saldmy.conferencemanagementrest.entity.Conference;
import com.saldmy.conferencemanagementrest.entity.ConferenceStatus;
import com.saldmy.conferencemanagementrest.entity.Registration;
import com.saldmy.conferencemanagementrest.exception.ConferenceNotFoundException;
import com.saldmy.conferencemanagementrest.repository.ConferenceRepository;
import com.saldmy.conferencemanagementrest.service.ConferenceService;
import com.saldmy.conferencemanagementrest.util.ConferenceUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
public class ConferenceServiceImpl implements ConferenceService {

    private final ConferenceRepository conferenceRepository;
    private final ConferenceUtils utils;

    public ConferenceServiceImpl(ConferenceRepository conferenceRepository, ConferenceUtils utils) {
        this.conferenceRepository = conferenceRepository;
        this.utils = utils;
    }

    @Override
    public Conference add(Conference newConference) {
        return conferenceRepository.save(newConference);
    }

    @Override
    public List<Conference> findAll() {
        return conferenceRepository.findAll();
    }

    @Override
    public Optional<Conference> find(Long id) {
        return conferenceRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        conferenceRepository.deleteById(id);
    }

    @Override
    public Conference update(Long id, Conference newConference) {
        return find(id)
                .map(conference -> {
                    conference.setTitle(newConference.getTitle());
                    conference.setPlace(newConference.getPlace());
                    conference.setMaxParticipants(newConference.getMaxParticipants());
                    conference.setStart(newConference.getStart());
                    conference.setDuration(newConference.getDuration());
                    conference.setStatus(newConference.getStatus());

                    Set<Registration> participants = conference.getRegistrations();
                    participants.clear();
                    participants.addAll(newConference.getRegistrations());

                    return add(conference);
                })
                .orElseThrow(() -> new ConferenceNotFoundException(id));
    }

    @Override
    public Conference changeStatus(Long id, ConferenceStatus newStatus) {
        return find(id)
                .map(conference -> {
                    utils.checkStatus(conference, newStatus);

                    conference.setStatus(newStatus);

                    return add(conference);
                })
                .orElseThrow(() -> new ConferenceNotFoundException(id));
    }

}
