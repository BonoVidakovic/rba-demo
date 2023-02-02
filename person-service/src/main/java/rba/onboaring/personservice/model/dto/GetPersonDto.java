package rba.onboaring.personservice.model.dto;

import lombok.Builder;
import lombok.Value;
import rba.onboaring.personservice.model.Status;
import rba.onboaring.personservice.model.entity.Person;

@Value
@Builder
public class GetPersonDto {
    public static GetPersonDto fromPerson(Person person) {
        return GetPersonDto.builder()
                .name(person.getName())
                .surname(person.getSurname())
                .oib(person.getOib())
                .status(person.getStatus())
                .build();
    }

    String name;
    String surname;
    String oib;
    Status status;
}