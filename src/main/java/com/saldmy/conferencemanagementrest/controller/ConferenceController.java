package com.saldmy.conferencemanagementrest.controller;

import com.saldmy.conferencemanagementrest.model.ConferenceModelAssembler;
import com.saldmy.conferencemanagementrest.exception.ConferenceNotFoundException;
import com.saldmy.conferencemanagementrest.entity.ConferenceStatus;
import com.saldmy.conferencemanagementrest.entity.Conference;
import com.saldmy.conferencemanagementrest.repository.ConferenceRepository;
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

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
public class ConferenceController {

    private final ConferenceRepository repository;
    private final ConferenceModelAssembler assembler;

    public ConferenceController(ConferenceRepository repository, ConferenceModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
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

    @PatchMapping("/conferences/{id}/status")
    public ResponseEntity<?> changeStatus(@PathVariable Long id, @RequestBody ConferenceStatus newStatus) {
        return repository.findById(id)
                .map(conference -> {
                    conference.setStatus(newStatus);
                    return ResponseEntity.ok().build();
                })
                .orElseThrow(() -> new ConferenceNotFoundException(id));
    }

}
