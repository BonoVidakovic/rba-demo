package rba.onboaring.personservice.model.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PrintCardDto {
    byte[] checksum;
    String filename;
    String content;
}
