package rba.onboaring.personservice.model.entity;

import jakarta.persistence.Entity;
import lombok.*;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Card extends BaseEntity {

    private String name;
    private LocalDateTime created_at;
    private Boolean active;
}
