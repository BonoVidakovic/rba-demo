package rba.onboaring.personservice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import rba.onboaring.personservice.config.AbstractCouchbaseTest;
import rba.onboaring.personservice.controller.PersonController;
import rba.onboaring.personservice.exception.UserAlreadyExistsException;
import rba.onboaring.personservice.model.dto.CreatePersonDto;
import rba.onboaring.personservice.model.entity.Person;
import rba.onboaring.personservice.repository.PersonRepository;

import java.util.Optional;

public class CreatePersonTests extends AbstractCouchbaseTest {

    @Autowired
    public PersonController personController;

    @Autowired
    public PersonRepository personRepository;

    @Test
    public void canCreateUser() {
        String testName = "test11";
        String testSurname = "test12";
        String testOib = "test13";
        CreatePersonDto createPersonDto = CreatePersonDto.builder()
                .name(testName)
                .surname(testSurname)
                .oib(testOib)
                .build();

        personController.createPersonDto(createPersonDto);

        Optional<Person> result = personRepository.findOneByOib(testOib);
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(testName, result.get().getName());
        Assertions.assertEquals(testSurname, result.get().getSurname());
        Assertions.assertEquals(testOib, result.get().getOib());
    }

    @Test
    public void throwsUserAlreadyExistsException_whenUserExists() {
        String testName = "test21";
        String testSurname = "test22";
        String testOib = "test23";
        CreatePersonDto createPersonDto = CreatePersonDto.builder()
                .name(testName)
                .surname(testSurname)
                .oib(testOib)
                .build();

        personController.createPersonDto(createPersonDto);

        Optional<Person> result = personRepository.findOneByOib(testOib);
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(testName, result.get().getName());
        Assertions.assertEquals(testSurname, result.get().getSurname());
        Assertions.assertEquals(testOib, result.get().getOib());

        Assertions.assertThrows(UserAlreadyExistsException.class, () -> personController.createPersonDto(createPersonDto));

    }
}
