package rba.onboaring.personservice.model.entity;

import jakarta.persistence.Entity;
import lombok.*;
import rba.onboaring.personservice.model.Status;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Person extends BaseEntity {

    private String name;
    private String surname;
    private String oib;
    private Status status;

}
