package rba.onboarding.writerservice.exception;

import org.slf4j.LoggerFactory;

public class FatalError extends Error {
    public FatalError(String msg) {
        super("Illegal service state: " + msg);
        LoggerFactory.getLogger("Person Service").error(msg);
    }
}
