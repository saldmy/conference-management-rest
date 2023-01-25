package com.saldmy.conferencemanagementrest.repository;

import com.saldmy.conferencemanagementrest.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {}
