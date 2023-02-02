package rba.onboaring.personservice.controller;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import rba.onboaring.personservice.exception.*;
import rba.onboaring.personservice.model.ApiError;

import java.util.Arrays;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final ConfigurableApplicationContext ctx;

    private final Logger log;

    @Autowired
    public GlobalExceptionHandler(ConfigurableApplicationContext ctx, Logger log) {
        this.ctx = ctx;
        this.log = log;
    }


    @ExceptionHandler({CardAlreadyActiveException.class, UserAlreadyExistsException.class})
    public ResponseEntity<ApiError> handleForbiddenActionException(Exception ex) {
        ApiError body = new ApiError("Action not permitted");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new ResponseEntity<>(body, headers, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({NoSuchPersonException.class, NoSuchActiveCardException.class})
    public ResponseEntity<ApiError> handleNotFoundException(Exception ex) {
        ApiError body = new ApiError("Object not found");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new ResponseEntity<>(body, headers, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({IssueContactingPeerException.class})
    public ResponseEntity<ApiError> handleInternalErrorException(Exception ex) {
        ApiError body = new ApiError("Part of infrastructure is down");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new ResponseEntity<>(body, headers, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler({FatalError.class})
    public void handleUnrecoverableFailure(FatalError error) {
        log.error("Unrecoverable application failure: " + error.getMessage());
        log.error(Arrays.toString(error.getStackTrace()));
        ctx.close();
    }
}
