package com.example.person.service;

import com.example.person.model.Person;

import java.util.List;

/**
 * Service for managing {@link com.example.person.model.Person} entities.
 */
public interface PersonService {

    List<Person> findAll();

    List<Person> findAllByLastName(String lastName);

    Person findOne(Long id);

    Person save(Person entity);
}
