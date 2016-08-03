package com.example.person.api;

import com.example.person.api.resource.PersonListResource;
import com.example.person.api.resource.PersonResource;
import com.example.person.model.Person;
import com.example.person.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.stream.Collectors;

/**
 * REST api for person resources.
 */
@RestController
@RequestMapping(path = "/persons", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class PersonController {

    private PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public PersonListResource findAllPersons() {
        return new PersonListResource(
                personService
                        .findAll()
                        .stream()
                        .map(PersonResource::new)
                        .collect(Collectors.toList()));
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> findOnePersonById(@PathVariable("id") Long id) {
        Person person = personService.findOne(id);
        if (person == null) {
            return ResponseEntity.notFound().build();
        } else {
            return new ResponseEntity<>(new PersonResource(person), HttpStatus.OK);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<PersonResource> createPerson(@RequestBody Person personResource) {
        Person person = personService.save(personResource);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(person.getId()).toUri());
        return new ResponseEntity<PersonResource>(new PersonResource(person), httpHeaders, HttpStatus.CREATED);
    }

}
