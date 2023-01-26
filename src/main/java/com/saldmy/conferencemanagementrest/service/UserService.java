package com.saldmy.conferencemanagementrest.service;

import com.saldmy.conferencemanagementrest.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {

    User add(User newUser);

    List<User> findAll();

    Optional<User> find(Long id);

    Optional<User> find(String email);

    void delete(Long id);

}
