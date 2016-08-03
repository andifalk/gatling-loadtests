package com.example.person.service;

import com.example.person.model.Person;
import com.example.person.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation for {@link PersonService}.
 */
@Service
@Transactional(readOnly = true)
public class PersonServiceImpl implements PersonService {

    private PersonRepository personRepository;

    @Autowired
    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public List<Person> findAll() {
        return personRepository.findAll(new Sort("lastName"));
    }

    @Override
    public List<Person> findAllByLastName(String lastName) {
        return personRepository.findAllByLastName(lastName);
    }

    @Override
    public Person findOne(Long id) {
        return personRepository.findOne(id);
    }

    @Override
    @Transactional
    public Person save(Person entity) {
        return personRepository.save(entity);
    }
}
