package com.example;

import com.example.person.model.Gender;
import com.example.person.model.Person;
import com.example.person.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * Initializes a lot of data.
 */
@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataInitializer.class);

    private static final String[] CHARS = new String[]
            {"a", "b", "c", "d", "e", "f", "g", "h", "i",
                    "j", "k", "l", "m", "n", "o", "p", "r", "s", "t", "u", "v", "w", "z"};

    private static final int CHAR_LENGTH = CHARS.length - 1;

    private PersonRepository personRepository;

    private Random random = new Random(200);

    @Value("${test.data.max}")
    private Long maxTestData;

    @Autowired
    public DataInitializer(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        for (long i=0; i < maxTestData; i++) {
            personRepository.save(
                    new Person(createRandomName(),
                            createRandomName(), (i % 2 == 0) ? Gender.MALE : Gender.FEMALE));
        }

        LOGGER.info("Created {} test data sets", maxTestData);
    }

    private String createRandomName() {
        StringBuilder builder = new StringBuilder();
        for (int i=0; i < 8; i++) {
            builder.append(CHARS[random.nextInt(CHAR_LENGTH)]);
        }
        return builder.toString();
    }
}
