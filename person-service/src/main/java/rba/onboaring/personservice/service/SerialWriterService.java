package rba.onboaring.personservice.service;

import org.springframework.stereotype.Service;
import rba.onboaring.personservice.model.dto.PrintCardDto;

@Service
public interface SerialWriterService {
    void printCard(PrintCardDto printCardDto);
}
