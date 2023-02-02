package rba.onboarding.writerservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import rba.onboarding.writerservice.exception.FatalError;

import java.security.KeyStore;
import java.security.PublicKey;
import java.security.cert.Certificate;

@Configuration
public class UtilityConfig {

    private final String certPassword;
    private final Resource certificate;

    public UtilityConfig(@Value("${security.cert_password}") String certPassword,
                         @Value("classpath:receiver_keystore.p12") Resource certificate) {
        this.certPassword = certPassword;
        this.certificate = certificate;
    }

    @Bean
    public PublicKey publicKey() {
        try {
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(certificate.getInputStream(), certPassword.toCharArray());
            Certificate certificate = keyStore.getCertificate("receiverKeyPair");
            return certificate.getPublicKey();
        } catch (Exception ex) {
            throw new FatalError("Unable to load certificate");
        }
    }
}
