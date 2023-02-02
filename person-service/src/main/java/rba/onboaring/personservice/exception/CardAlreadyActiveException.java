package rba.onboaring.personservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class CardAlreadyActiveException extends RuntimeException {
    public CardAlreadyActiveException(String cardName) {
        super("Card " + cardName + "already active");
    }
}
