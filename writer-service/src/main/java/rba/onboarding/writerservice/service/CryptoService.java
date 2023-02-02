package rba.onboarding.writerservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rba.onboarding.writerservice.exception.BadSignatureException;
import rba.onboarding.writerservice.exception.FatalError;

import java.nio.charset.StandardCharsets;
import java.security.PublicKey;
import java.security.Signature;

@Service
public class CryptoService {

    private final PublicKey publicKey;

    @Autowired
    public CryptoService(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public void validateSignature(String content, byte[] checksum) {
        try {
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initVerify(publicKey);
            signature.update(content.getBytes(StandardCharsets.UTF_8));
            if (!signature.verify(checksum)) {
                throw new BadSignatureException();
            }
        } catch (Exception ex) {
            throw new FatalError("Signing algorithm unsupported");
        }
    }
}
