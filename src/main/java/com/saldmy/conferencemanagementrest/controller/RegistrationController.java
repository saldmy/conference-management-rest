package com.saldmy.conferencemanagementrest.controller;

import com.saldmy.conferencemanagementrest.entity.Registration;
import com.saldmy.conferencemanagementrest.entity.RegistrationId;
import com.saldmy.conferencemanagementrest.exception.ConferenceNotFoundException;
import com.saldmy.conferencemanagementrest.exception.RegistrationNotFoundException;
import com.saldmy.conferencemanagementrest.exception.UserNotFoundException;
import com.saldmy.conferencemanagementrest.model.RegistrationModelAssembler;
import com.saldmy.conferencemanagementrest.repository.ConferenceRepository;
import com.saldmy.conferencemanagementrest.repository.RegistrationRepository;
import com.saldmy.conferencemanagementrest.repository.UserRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
public class RegistrationController {

    private final RegistrationRepository repository;
    private final UserRepository userRepository;
    private final ConferenceRepository conferenceRepository;
    private final RegistrationModelAssembler assembler;

    public RegistrationController(RegistrationRepository repository, UserRepository userRepository, ConferenceRepository conferenceRepository, RegistrationModelAssembler assembler) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.conferenceRepository = conferenceRepository;
        this.assembler = assembler;
    }

    @GetMapping("/registrations")
    public CollectionModel<EntityModel<Registration>> all() {
        List<EntityModel<Registration>> registrations = repository.findAll().stream()
                .map(assembler::toModel)
                .toList();

        return CollectionModel.of(
                registrations,
                linkTo(methodOn(ConferenceController.class).all()).withSelfRel()
        );
    }

    @GetMapping("/registrations/u={userId}c={conferenceId}")
    public EntityModel<Registration> one(@PathVariable Long userId, @PathVariable Long conferenceId) {
        RegistrationId id = new RegistrationId(userId, conferenceId);

        Registration registration = repository.findById(id)
                .orElseThrow(() -> new RegistrationNotFoundException(id));

        return assembler.toModel(registration);
    }

    @PutMapping("/registrations/u={userId}c={conferenceId}")
    public ResponseEntity<?> newRegistration(@PathVariable Long userId, @PathVariable Long conferenceId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        conferenceRepository.findById(conferenceId)
                .orElseThrow(() -> new ConferenceNotFoundException(conferenceId));

        Registration registration = repository.save(new Registration(new RegistrationId(userId, conferenceId)));

        return ResponseEntity.ok(assembler.toModel(registration));
    }

    @DeleteMapping("/registrations/u={userId}c={conferenceId}")
    public ResponseEntity<?> deleteRegistration(@PathVariable Long userId, @PathVariable Long conferenceId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        conferenceRepository.findById(conferenceId)
                .orElseThrow(() -> new ConferenceNotFoundException(conferenceId));

        RegistrationId id = new RegistrationId(userId, conferenceId);

        Registration registration = repository.findById(id)
                .orElseThrow(() -> new RegistrationNotFoundException(id));

        repository.delete(registration);

        return ResponseEntity.ok().build();
    }

}
