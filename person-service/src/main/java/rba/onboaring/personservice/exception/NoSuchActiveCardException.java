package rba.onboaring.personservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoSuchActiveCardException extends RuntimeException {
    public NoSuchActiveCardException(String cardName) {
        super("Card " + cardName + " not found");
    }
}
