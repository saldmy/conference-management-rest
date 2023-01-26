package com.saldmy.conferencemanagementrest.service;

import com.saldmy.conferencemanagementrest.entity.Registration;
import com.saldmy.conferencemanagementrest.entity.RegistrationId;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RegistrationService {

    Registration add(Registration newRegistration);

    Registration add (Long userId, Long conferenceId);

    List<Registration> findAll();

    Registration find(RegistrationId id);

    Registration find(Long userId, Long conferenceId);

    void delete(RegistrationId id);

}
