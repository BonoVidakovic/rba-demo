package rba.onboaring.personservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rba.onboaring.personservice.model.entity.Card;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CardRepository extends JpaRepository<Card, UUID> {

    Optional<Card> findOneByNameAndActiveIsTrue(String name);
}
