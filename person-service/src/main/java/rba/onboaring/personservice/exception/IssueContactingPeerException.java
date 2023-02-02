package rba.onboaring.personservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
public class IssueContactingPeerException extends RuntimeException {
    public IssueContactingPeerException(String peerName) {
        super("Unable to reach peer: " + peerName);
    }
}
