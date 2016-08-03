package com.example.person.api;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.person.model.Gender;
import com.example.person.model.Person;
import com.example.person.service.PersonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

/**
 * Unit test for {@link PersonController}.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(PersonController.class)
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService personService;

    @Before
    public void initMocks() {

        Person person1 = new Person("Hans", "Mustermann", Gender.MALE);
        Person person2 = new Person("Sabine", "Maier", Gender.FEMALE);

        ReflectionTestUtils.setField(person1, "id", 1L);
        ReflectionTestUtils.setField(person2, "id", 2L);

        when(personService.findAll()).thenReturn(Arrays.asList(person1, person2));
        when(personService.findOne(eq(1L))).thenReturn(person1);
        when(personService.save(any(Person.class))).thenReturn(person1);
    }

    @Test
    public void findAllPersons() throws Exception {
        this.mockMvc
                .perform(get("/persons").accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.personList").isArray())
                .andExpect(jsonPath("$._embedded.personList.length()").value(is(2)))
                .andExpect(jsonPath("$._embedded.personList[0].firstName").value(is("Hans")))
                .andExpect(jsonPath("$._embedded.personList[0].lastName").value(is("Mustermann")))
                .andExpect(jsonPath("$._embedded.personList[0].gender").value(is(Gender.MALE.name())));
    }

    @Test
    public void findOnePersonById() throws Exception {
        this.mockMvc
                .perform(get("/persons/1").accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value(is("Hans")))
                .andExpect(jsonPath("$.lastName").value(is("Mustermann")))
                .andExpect(jsonPath("$.gender").value(is(Gender.MALE.name())));
    }

    @Test
    public void createPerson() throws Exception {

        Person personResource = new Person("Hans", "Mustermann", Gender.MALE);

        this.mockMvc
                .perform(post("/persons")
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .content(new ObjectMapper().writeValueAsString(personResource)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value(is("Hans")))
                .andExpect(jsonPath("$.lastName").value(is("Mustermann")))
                .andExpect(jsonPath("$.gender").value(is(Gender.MALE.name())));
    }

}