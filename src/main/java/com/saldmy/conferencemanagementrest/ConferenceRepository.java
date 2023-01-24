package com.saldmy.conferencemanagementrest;

import org.springframework.data.jpa.repository.JpaRepository;

interface ConferenceRepository extends JpaRepository<Conference, Long> {}
