package rba.onboaring.personservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "services")
@Configuration
@Data
public class ServicesProps {
    PeerInfo serialWriter;
}
