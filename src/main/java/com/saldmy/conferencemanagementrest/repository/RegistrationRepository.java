package com.saldmy.conferencemanagementrest.repository;

import com.saldmy.conferencemanagementrest.entity.Registration;
import com.saldmy.conferencemanagementrest.entity.RegistrationId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistrationRepository extends JpaRepository<Registration, RegistrationId> {}
