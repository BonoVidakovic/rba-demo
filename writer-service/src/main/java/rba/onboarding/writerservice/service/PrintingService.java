package rba.onboarding.writerservice.service;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;

@Service
public class PrintingService extends BaseService {

    @Autowired
    public PrintingService(Logger log) {
        super(log);
    }

    public void print(String filename, String content) {
        try {
            FileWriter myWriter = new FileWriter(filename + ".dat");
            myWriter.write(content);
            myWriter.close();
            log.info("Success writing file: " + filename);
        } catch (IOException e) {
            log.warn("Failed writing file: " + filename);
        }
    }

}
