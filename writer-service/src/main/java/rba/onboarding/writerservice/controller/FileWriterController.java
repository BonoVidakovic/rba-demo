package rba.onboarding.writerservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import rba.onboarding.writerservice.model.FileDto;
import rba.onboarding.writerservice.service.CryptoService;
import rba.onboarding.writerservice.service.PrintingService;

@RestController()
public class FileWriterController {

    @Autowired
    private PrintingService printingService;
    @Autowired
    private CryptoService cryptoService;

    @PostMapping("/")
    public void printFile(@RequestBody FileDto fileDto) {
        cryptoService.validateSignature(fileDto.getContent(), fileDto.getChecksum());
        printingService.print(fileDto.getFilename(), fileDto.getContent());
    }
}
