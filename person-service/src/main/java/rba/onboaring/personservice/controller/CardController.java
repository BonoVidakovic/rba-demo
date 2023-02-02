package rba.onboaring.personservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import rba.onboaring.personservice.service.CardService;

@RestController("/card")
public class CardController {

    private final CardService cardService;

    @Autowired
    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping("/create")
    public void createCard(String oib) {
        cardService.createCard(oib);
    }
}
