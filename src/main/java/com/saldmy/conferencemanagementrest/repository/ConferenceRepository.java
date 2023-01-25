package com.saldmy.conferencemanagementrest.repository;

import com.saldmy.conferencemanagementrest.entity.Conference;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConferenceRepository extends JpaRepository<Conference, Long> {}
