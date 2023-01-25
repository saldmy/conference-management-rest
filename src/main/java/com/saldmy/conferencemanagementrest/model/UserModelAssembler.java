package com.saldmy.conferencemanagementrest.model;

import com.saldmy.conferencemanagementrest.controller.ConferenceController;
import com.saldmy.conferencemanagementrest.entity.User;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserModelAssembler implements RepresentationModelAssembler<User, EntityModel<User>> {

    @Override
    public EntityModel<User> toModel(User user) {
        return EntityModel.of(user,
                linkTo(methodOn(ConferenceController.class).one(user.getId())).withSelfRel(),
                linkTo(methodOn(ConferenceController.class).all()).withRel("users")
        );
    }

}
