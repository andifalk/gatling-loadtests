package com.example.person.api.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import com.example.person.api.PersonController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;

/**
 * Resource for a collection of {@link PersonResource person resources}.
 */
public class PersonListResource extends Resources<PersonResource> {

    public PersonListResource(Iterable<PersonResource> content, Link... links) {
        super(content, links);
        this.add(linkTo(methodOn(PersonController.class).findAllPersons()).withSelfRel());
    }
}
