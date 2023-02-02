package rba.onboaring.personservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import rba.onboaring.personservice.config.ServicesProps;
import rba.onboaring.personservice.exception.FatalError;
import rba.onboaring.personservice.exception.IssueContactingPeerException;
import rba.onboaring.personservice.model.dto.PrintCardDto;

@Service
public class SerialWriterServiceImpl extends BaseService implements SerialWriterService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final ServicesProps servicesProps;

    @Autowired
    public SerialWriterServiceImpl(Logger log, RestTemplate restTemplate, ObjectMapper objectMapper, ServicesProps servicesProps) {
        super(log);
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.servicesProps = servicesProps;
    }

    @Override
    public void printCard(PrintCardDto printCardDto) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            String body = objectMapper.writeValueAsString(printCardDto);
            HttpEntity<String> request = new HttpEntity<>(body, headers);

            String printUrl = servicesProps.getSerialWriter().getUrl();

            ResponseEntity<String> response = restTemplate.postForEntity(
                    printUrl,
                    request,
                    String.class
            );

            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new FatalError("Service " + servicesProps.getSerialWriter().getName() + " response out of spec");
            }
        } catch (JsonProcessingException e) {
            throw new FatalError("Unable to serialize card printing request");
        } catch (RestClientException ex) {
            throw new IssueContactingPeerException(servicesProps.getSerialWriter().getName());
        }
    }
}
