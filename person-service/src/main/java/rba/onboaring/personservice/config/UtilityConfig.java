package rba.onboaring.personservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.web.client.RestTemplate;
import rba.onboaring.personservice.exception.FatalError;

import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;

@Configuration
public class UtilityConfig {

    private final String certPassword;

    private final Resource keystoreFile;

    @Autowired
    public UtilityConfig(@Value("${security.cert_password}") String certPassword,
                         @Value("classpath:sender_keystore.p12") Resource keystoreFile) {
        this.certPassword = certPassword;
        this.keystoreFile = keystoreFile;
    }


    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public MessageDigest messageDigester() {
        try {
            return MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException ex) {
            throw new FatalError("JVM doesn't support SHA-256");
        }
    }

    @Bean
    public PrivateKey privateKey() {
        try {
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(keystoreFile.getInputStream(), certPassword.toCharArray());
            return (PrivateKey) keyStore.getKey("senderKeyPair", certPassword.toCharArray());
        } catch (Exception ex) {
            throw new FatalError("Unable to load certificate");
        }
    }
}
