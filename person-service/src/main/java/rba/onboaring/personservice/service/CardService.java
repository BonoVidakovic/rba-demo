package rba.onboaring.personservice.service;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rba.onboaring.personservice.exception.CardAlreadyActiveException;
import rba.onboaring.personservice.exception.FatalError;
import rba.onboaring.personservice.exception.NoSuchActiveCardException;
import rba.onboaring.personservice.exception.NoSuchPersonException;
import rba.onboaring.personservice.model.Status;
import rba.onboaring.personservice.model.dto.PrintCardDto;
import rba.onboaring.personservice.model.entity.Card;
import rba.onboaring.personservice.model.entity.Person;
import rba.onboaring.personservice.repository.CardRepository;
import rba.onboaring.personservice.repository.PersonRepository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class CardService extends BaseService {

    private final CardRepository cardRepository;
    private final PersonRepository personRepository;
    private final CryptoService cryptoService;
    private final SerialWriterService serialWriterService;

    @Autowired
    public CardService(Logger log, CardRepository cardRepository, PersonRepository personRepository, CryptoService cryptoService, SerialWriterService serialWriterService) {
        super(log);
        this.cardRepository = cardRepository;
        this.personRepository = personRepository;
        this.cryptoService = cryptoService;
        this.serialWriterService = serialWriterService;
    }

    @Transactional
    public void createCard(String oib) {
        Person person = personRepository.findOneByOib(oib)
                .orElseThrow(() -> new NoSuchPersonException(oib));

        String cardName = getCardFileName(person);

        Optional<Card> activeCard = cardRepository.findOneByNameAndActiveIsTrue(cardName);
        if (activeCard.isPresent()) {
            log.warn("Attempted creating already active card: " + cardName);
            throw new CardAlreadyActiveException(cardName);
        }

        String content = person.getOib() + "," + person.getName() + "," + person.getSurname() + ";";
        byte[] checksum = cryptoService.sign(content);

        PrintCardDto printCardDto = PrintCardDto.builder()
                .filename(cardName)
                .checksum(checksum)
                .content(content)
                .build();

        serialWriterService.printCard(printCardDto);

        Card newCard = Card.builder()
                .created_at(LocalDateTime.now())
                .active(true)
                .name(cardName)
                .build();

        cardRepository.save(newCard);

        person.setStatus(Status.CARD_CREATED);
        personRepository.save(person);

        log.info("Printed card: " + cardName);
    }

    public void inactivateCard(UUID personId) {
        Person person = personRepository.findById(personId)
                .orElseThrow(() -> new FatalError("Attempting to inactivate card of nonexistent person"));

        String cardName = getCardFileName(person);

        Card card = cardRepository.findOneByNameAndActiveIsTrue(cardName)
                .orElseThrow(() -> new NoSuchActiveCardException(cardName));

        card.setActive(false);

        cardRepository.save(card);
    }

    private String getCardFileName(Person person) {
        String plaintext = person.getName() + person.getSurname() + person.getOib();
        return cryptoService.generateHash(plaintext);
    }

}
