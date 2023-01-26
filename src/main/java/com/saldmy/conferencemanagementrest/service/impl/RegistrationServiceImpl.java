package com.saldmy.conferencemanagementrest.service.impl;

import com.saldmy.conferencemanagementrest.entity.Registration;
import com.saldmy.conferencemanagementrest.entity.RegistrationId;
import com.saldmy.conferencemanagementrest.exception.ConferenceNotFoundException;
import com.saldmy.conferencemanagementrest.exception.RegistrationNotFoundException;
import com.saldmy.conferencemanagementrest.exception.UserNotFoundException;
import com.saldmy.conferencemanagementrest.repository.RegistrationRepository;
import com.saldmy.conferencemanagementrest.service.ConferenceService;
import com.saldmy.conferencemanagementrest.service.RegistrationService;
import com.saldmy.conferencemanagementrest.service.UserService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RegistrationServiceImpl implements RegistrationService {

    private final RegistrationRepository repository;
    private final UserService userService;
    private final ConferenceService conferenceService;

    public RegistrationServiceImpl(RegistrationRepository repository, UserService userService, ConferenceService conferenceService) {
        this.repository = repository;
        this.userService = userService;
        this.conferenceService = conferenceService;
    }

    @Override
    public Registration add(Registration newRegistration) {
        Long userId = newRegistration.getId().getUserId();
        userService.find(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        Long conferenceId = newRegistration.getId().getConferenceId();
        conferenceService.find(conferenceId)
                .orElseThrow(() -> new ConferenceNotFoundException(conferenceId));

        return repository.save(newRegistration);
    }

    @Override
    public Registration add(Long userId, Long conferenceId) {
        userService.find(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        conferenceService.find(conferenceId)
                .orElseThrow(() -> new ConferenceNotFoundException(conferenceId));

        return repository.save(new Registration(new RegistrationId(userId, conferenceId)));
    }

    @Override
    public List<Registration> findAll() {
        return repository.findAll();
    }

    @Override
    public Registration find(RegistrationId id) {
        return repository.findById(id).orElseThrow(() -> new RegistrationNotFoundException(id));
    }

    @Override
    public Registration find(Long userId, Long conferenceId) {
        return find(new RegistrationId(userId, conferenceId));
    }

    @Override
    public void delete(RegistrationId id) {
        repository.delete(find(id));
    }

}
