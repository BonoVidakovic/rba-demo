package rba.onboarding.writerservice.model;

import lombok.Value;

@Value
public class FileDto {
    byte[] checksum;
    String filename;
    String content;
}
