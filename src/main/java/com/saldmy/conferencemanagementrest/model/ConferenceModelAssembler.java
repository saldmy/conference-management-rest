package com.saldmy.conferencemanagementrest.model;

import com.saldmy.conferencemanagementrest.controller.ConferenceController;
import com.saldmy.conferencemanagementrest.entity.Conference;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ConferenceModelAssembler implements RepresentationModelAssembler<Conference, EntityModel<Conference>> {

    @Override
    public EntityModel<Conference> toModel(Conference conference) {
        return EntityModel.of(conference,
                linkTo(methodOn(ConferenceController.class).one(conference.getId())).withSelfRel(),
                linkTo(methodOn(ConferenceController.class).all()).withRel("conferences"),
                linkTo(methodOn(ConferenceController.class).availableSeats(conference.getId())).withRel("availableSeats"),
                linkTo(methodOn(ConferenceController.class).status(conference.getId())).withRel("status")
        );
    }

}
