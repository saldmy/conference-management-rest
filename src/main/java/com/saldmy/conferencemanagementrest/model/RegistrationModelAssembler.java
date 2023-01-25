package com.saldmy.conferencemanagementrest.model;

import com.saldmy.conferencemanagementrest.controller.RegistrationController;
import com.saldmy.conferencemanagementrest.entity.Registration;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class RegistrationModelAssembler implements RepresentationModelAssembler<Registration, EntityModel<Registration>> {

    @Override
    public EntityModel<Registration> toModel(Registration registration) {
        return EntityModel.of(registration,
                linkTo(methodOn(RegistrationController.class).one(
                        registration.getId().getUserId(),
                        registration.getId().getConferenceId()
                )).withSelfRel(),
                linkTo(methodOn(RegistrationController.class).all()).withRel("registrations")
        );
    }

}
