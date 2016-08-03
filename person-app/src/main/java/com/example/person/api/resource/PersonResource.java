package com.example.person.api.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import com.example.person.api.PersonController;
import com.example.person.model.Person;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

/**
 * Resource for {@link Person} entity.
 */
public class PersonResource extends Resource<Person> {

    public PersonResource(Person content, Link... links) {
        super(content, links);
        this.add(linkTo(methodOn(PersonController.class).findOnePersonById(
                content.getId())).withSelfRel());
    }
}
