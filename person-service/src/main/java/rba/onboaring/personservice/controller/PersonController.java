package rba.onboaring.personservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rba.onboaring.personservice.model.dto.CreatePersonDto;
import rba.onboaring.personservice.model.dto.GetPersonDto;
import rba.onboaring.personservice.service.PersonService;

@RestController("/person")
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping
    public CreatePersonDto createPerson(@RequestBody CreatePersonDto personDto) {
        return personService.createPerson(personDto);
    }

    @DeleteMapping("/{oib}")
    public void deletePerson(@PathVariable String oib) {
        personService.deletePerson(oib);
    }

    @GetMapping("/{oib}")
    public GetPersonDto readPerson(String oib) {
        return personService.getPerson(oib);
    }

}
