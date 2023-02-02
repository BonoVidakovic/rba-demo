package rba.onboaring.personservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoSuchPersonException extends RuntimeException {
    public NoSuchPersonException(String oib) {
        super("Person with oib " + oib + " not found");
    }
}
