package rba.onboaring.personservice.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import rba.onboaring.personservice.model.entity.Person;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreatePersonDto {
    public static CreatePersonDto fromPerson(Person person) {
        return CreatePersonDto.builder()
                .name(person.getName())
                .surname(person.getSurname())
                .oib(person.getOib())
                .build();
    }

    @NotBlank(message = "name must not be blank")
    private String name;
    @NotBlank(message = "surname must not be blank")
    private String surname;
    @NotBlank(message = "oib must not be blank")
    private String oib;
}
