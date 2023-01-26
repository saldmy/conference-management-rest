package com.saldmy.conferencemanagementrest.service.impl;

import com.saldmy.conferencemanagementrest.entity.User;
import com.saldmy.conferencemanagementrest.repository.UserRepository;
import com.saldmy.conferencemanagementrest.service.UserService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User add(User newUser) {
        return repository.save(newUser);
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<User> find(Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<User> find(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

}
