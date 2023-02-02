package rba.onboaring.personservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rba.onboaring.personservice.exception.FatalError;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.Signature;

@Service
public class CryptoService {

    private final MessageDigest messageDigester;
    private final PrivateKey privateKey;

    @Autowired
    public CryptoService(
            MessageDigest messageDigester,
            PrivateKey privateKey) {
        this.messageDigester = messageDigester;
        this.privateKey = privateKey;
    }

    public String generateHash(String plaintext) {
        byte[] messageDigest = messageDigester.digest(plaintext.getBytes(StandardCharsets.UTF_8));

        return convertToHex(messageDigest);
    }

    public byte[] sign(String plaintext) {
        try {
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initSign(privateKey);
            signature.update(plaintext.getBytes(StandardCharsets.UTF_8));
            return signature.sign();
        } catch (Exception ex) {
            throw new FatalError("Unable to sign text");
        }
    }

    private String convertToHex(final byte[] messageDigest) {
        BigInteger bigint = new BigInteger(1, messageDigest);
        String hexText = bigint.toString(16);
        while (hexText.length() < 32) {
            hexText = "0".concat(hexText);
        }
        return hexText;
    }
}
