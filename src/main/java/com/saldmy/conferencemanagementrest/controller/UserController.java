package com.saldmy.conferencemanagementrest.controller;

import com.saldmy.conferencemanagementrest.entity.Conference;
import com.saldmy.conferencemanagementrest.entity.User;
import com.saldmy.conferencemanagementrest.exception.ConferenceNotFoundException;
import com.saldmy.conferencemanagementrest.exception.UserDoesNotParticipateException;
import com.saldmy.conferencemanagementrest.exception.UserNotFoundException;
import com.saldmy.conferencemanagementrest.model.UserModelAssembler;
import com.saldmy.conferencemanagementrest.repository.ConferenceRepository;
import com.saldmy.conferencemanagementrest.repository.UserRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserController {

    private final UserRepository repository;
    private final ConferenceRepository conferenceRepository;
    private final UserModelAssembler assembler;

    public UserController(UserRepository repository, ConferenceRepository conferenceRepository, UserModelAssembler assembler) {
        this.repository = repository;
        this.conferenceRepository = conferenceRepository;
        this.assembler = assembler;
    }

    @GetMapping("/users")
    public CollectionModel<EntityModel<User>> all() {
        List<EntityModel<User>> users = repository.findAll().stream()
                .map(assembler::toModel)
                .toList();

        return CollectionModel.of(users, linkTo(methodOn(UserController.class).all()).withSelfRel());
    }

    @PostMapping("/users")
    public ResponseEntity<?> newUser(@RequestBody User newUser) {
        EntityModel<User> entityModel = assembler.toModel(repository.save(newUser));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @GetMapping("/users/{id}")
    public EntityModel<User> one(@PathVariable Long id) {
        User conference = repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        return assembler.toModel(conference);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<?> replaceUser(@PathVariable Long id, @RequestBody User newUser) {
        User updatedUser = repository.findById(id)
                .map(user -> {
                    user.setPassword(newUser.getPassword());
                    user.setFirstName(newUser.getFirstName());
                    user.setLastName(newUser.getLastName());
                    user.setDateOfBirth(newUser.getDateOfBirth());

                    return repository.save(user);
                })
                .orElseThrow(() -> new UserNotFoundException(id));

        EntityModel<User> entityModel = assembler.toModel(updatedUser);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/users/{userId}/conferences/{conferenceId}")
    public ResponseEntity<?> participate(@PathVariable Long userId, @PathVariable Long conferenceId) {
        return repository.findById(userId)
                .map(user -> {
                    Conference newConference = conferenceRepository.findById(conferenceId)
                            .orElseThrow(() -> new ConferenceNotFoundException(conferenceId));

                    user.getConferences().add(newConference);

                    repository.save(user);

                    return ResponseEntity.ok().build();
                })
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    @DeleteMapping("/users/{userId}/conferences/{conferenceId}")
    public ResponseEntity<?> withdrawParticipation(@PathVariable Long userId, @PathVariable Long conferenceId) {
        return repository.findById(userId)
                .map(user -> {
                    Conference conference = conferenceRepository.findById(conferenceId)
                            .orElseThrow(() -> new ConferenceNotFoundException(conferenceId));

                    Set<Conference> conferences = user.getConferences();

                    if (conferences.stream().noneMatch(conference::equals)) {
                        throw new UserDoesNotParticipateException(userId, conferenceId);
                    }

                    conferences.remove(conference);

                    repository.save(user);

                    return ResponseEntity.ok().build();
                })
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

}
