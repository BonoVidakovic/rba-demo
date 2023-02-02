package rba.onboarding.writerservice.controller;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import rba.onboarding.writerservice.exception.BadSignatureException;
import rba.onboarding.writerservice.exception.FatalError;
import rba.onboarding.writerservice.exception.FileWritingFailedException;
import rba.onboarding.writerservice.model.ApiError;

import java.util.Arrays;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    ConfigurableApplicationContext ctx;

    @Autowired
    Logger log;

    @ExceptionHandler({BadSignatureException.class})
    public ResponseEntity<ApiError> handleUnauthenticatedException(Exception ex) {
        ApiError body = new ApiError("Authentication failed");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new ResponseEntity<>(body, headers, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({FileWritingFailedException.class})
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
