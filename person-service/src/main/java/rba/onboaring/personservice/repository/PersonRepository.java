package rba.onboaring.personservice.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rba.onboaring.personservice.model.entity.Person;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PersonRepository extends JpaRepository<Person, UUID> {

    Optional<Person> findOneByOib(String oib);
}
