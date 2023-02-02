package rba.onboaring.personservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rba.onboaring.personservice.exception.NoSuchPersonException;
import rba.onboaring.personservice.exception.UserAlreadyExistsException;
import rba.onboaring.personservice.model.Status;
import rba.onboaring.personservice.model.dto.CreatePersonDto;
import rba.onboaring.personservice.model.dto.GetPersonDto;
import rba.onboaring.personservice.model.entity.Person;
import rba.onboaring.personservice.repository.PersonRepository;

import java.util.Optional;

@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final CardService cardService;

    @Autowired
    public PersonService(PersonRepository personRepository, CardService cardService) {
        this.personRepository = personRepository;
        this.cardService = cardService;
    }

    public CreatePersonDto createPerson(CreatePersonDto personDto) {
        personRepository.findOneByOib(personDto.getOib())
                .ifPresent((person) -> {
                    throw new UserAlreadyExistsException();
                });

        Person person = Person.builder()
                .name(personDto.getName())
                .surname(personDto.getSurname())
                .oib(personDto.getOib())
                .status(Status.ACTIVE)
                .build();

        person = personRepository.save(person);

        return CreatePersonDto.fromPerson(person);
    }

    public void deletePerson(String oib) {
        Optional<Person> person = personRepository.findOneByOib(oib);

        if (person.isPresent()) {
            if (person.get().getStatus() == Status.CARD_CREATED) {
                cardService.inactivateCard(person.get().getId());
            }
            personRepository.deleteById(person.get().getId());
        }
    }

    public GetPersonDto getPerson(String oib) {
        Person person = personRepository.findOneByOib(oib)
                .orElseThrow(() -> new NoSuchPersonException(oib));

        return GetPersonDto.fromPerson(person);
    }
}
