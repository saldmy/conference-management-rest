package com.saldmy.conferencemanagementrest.controller;

import com.saldmy.conferencemanagementrest.entity.Registration;
import com.saldmy.conferencemanagementrest.model.ConferenceModelAssembler;
import com.saldmy.conferencemanagementrest.exception.ConferenceNotFoundException;
import com.saldmy.conferencemanagementrest.entity.ConferenceStatus;
import com.saldmy.conferencemanagementrest.entity.Conference;
import com.saldmy.conferencemanagementrest.repository.ConferenceRepository;
import com.saldmy.conferencemanagementrest.util.ConferenceControllerUtils;
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

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
public class ConferenceController {

    private final ConferenceRepository repository;
    private final ConferenceModelAssembler assembler;
    private final ConferenceControllerUtils utils;

    public ConferenceController(ConferenceRepository repository, ConferenceModelAssembler assembler, ConferenceControllerUtils utils) {
        this.repository = repository;
        this.assembler = assembler;
        this.utils = utils;
    }

    @GetMapping("/conferences")
    public CollectionModel<EntityModel<Conference>> all() {
        List<EntityModel<Conference>> conferences = repository.findAll().stream()
                .map(assembler::toModel)
                .toList();

        return CollectionModel.of(conferences, linkTo(methodOn(ConferenceController.class).all()).withSelfRel());
    }

    @PostMapping("/conferences")
    public ResponseEntity<?> newConference(@RequestBody Conference newConference) {
        EntityModel<Conference> entityModel = assembler.toModel(repository.save(newConference));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @GetMapping("/conferences/{id}")
    public EntityModel<Conference> one(@PathVariable Long id) {
        Conference conference = repository.findById(id)
                .orElseThrow(() -> new ConferenceNotFoundException(id));

        return assembler.toModel(conference);
    }

    @PutMapping("/conferences/{id}")
    public ResponseEntity<?> replaceConference(@PathVariable Long id, @RequestBody Conference newConference) {
        Conference updatedConference = repository.findById(id)
                .map(conference -> {
                    conference.setTitle(newConference.getTitle());
                    conference.setPlace(newConference.getPlace());
                    conference.setMaxParticipants(newConference.getMaxParticipants());
                    conference.setStart(newConference.getStart());
                    conference.setDuration(newConference.getDuration());
                    conference.setStatus(newConference.getStatus());

                    Set<Registration> participants = conference.getRegistrations();
                    participants.clear();
                    participants.addAll(newConference.getRegistrations());

                    return repository.save(conference);
                })
                .orElseThrow(() -> new ConferenceNotFoundException(id));

        EntityModel<Conference> entityModel = assembler.toModel(updatedConference);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @DeleteMapping("/conferences/{id}")
    public ResponseEntity<?> deleteConference(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/conferences/{id}/available_seats")
    public ResponseEntity<Integer> availableSeats(@PathVariable Long id) {
        Conference conference = repository.findById(id)
                .orElseThrow(() -> new ConferenceNotFoundException(id));

        return ResponseEntity.ok().body(conference.getMaxParticipants() - conference.getRegistrations().size());
    }

    @GetMapping("/conferences/{id}/status")
    public ResponseEntity<String> status(@PathVariable Long id) {
        return ResponseEntity.ok().body(
                repository.findById(id)
                        .map(conference -> conference.getStatus().toString())
                        .orElseThrow(() -> new ConferenceNotFoundException(id))
        );
    }

    @PatchMapping("/conferences/{id}/status")
    public ResponseEntity<?> changeStatus(@PathVariable Long id, @RequestBody ConferenceStatus newStatus) {
        return repository.findById(id)
                .map(conference -> {
                    utils.checkStatus(conference, newStatus);

                    conference.setStatus(newStatus);
                    repository.save(conference);

                    return ResponseEntity.ok().build();
                })
                .orElseThrow(() -> new ConferenceNotFoundException(id));
    }

}
