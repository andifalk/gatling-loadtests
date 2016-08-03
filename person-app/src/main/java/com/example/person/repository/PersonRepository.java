package com.example.person.repository;

import com.example.person.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository for {@link Person} entities.
 */
public interface PersonRepository extends JpaRepository<Person, Long> {

    List<Person> findAllByLastName(String lastName);
}
