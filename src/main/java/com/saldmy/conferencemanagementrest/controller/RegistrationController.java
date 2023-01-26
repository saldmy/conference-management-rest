package com.saldmy.conferencemanagementrest.controller;

import com.saldmy.conferencemanagementrest.entity.Registration;
import com.saldmy.conferencemanagementrest.entity.RegistrationId;
import com.saldmy.conferencemanagementrest.model.RegistrationModelAssembler;
import com.saldmy.conferencemanagementrest.service.RegistrationService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class RegistrationController {

    private final RegistrationService registrationService;
    private final RegistrationModelAssembler assembler;

    public RegistrationController(RegistrationService registrationService, RegistrationModelAssembler assembler) {
        this.registrationService = registrationService;
        this.assembler = assembler;
    }

    @GetMapping("/registrations")
    public CollectionModel<EntityModel<Registration>> all() {
        List<EntityModel<Registration>> registrations = registrationService.findAll().stream()
                .map(assembler::toModel)
                .toList();

        return CollectionModel.of(
                registrations,
                linkTo(methodOn(ConferenceController.class).all()).withSelfRel()
        );
    }

    @GetMapping("/registrations/u={userId}c={conferenceId}")
    public EntityModel<Registration> one(@PathVariable Long userId, @PathVariable Long conferenceId) {
        return assembler.toModel(registrationService.find(userId, conferenceId));
    }

    @PutMapping("/registrations/u={userId}c={conferenceId}")
    public ResponseEntity<?> newRegistration(@PathVariable Long userId, @PathVariable Long conferenceId) {
        return ResponseEntity.ok(assembler.toModel(registrationService.add(userId, conferenceId)));
    }

    @DeleteMapping("/registrations/u={userId}c={conferenceId}")
    public ResponseEntity<?> deleteRegistration(@PathVariable Long userId, @PathVariable Long conferenceId) {
        registrationService.delete(new RegistrationId(userId, conferenceId));

        return ResponseEntity.ok().build();
    }

}
