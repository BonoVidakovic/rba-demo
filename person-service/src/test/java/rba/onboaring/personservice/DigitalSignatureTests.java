package rba.onboaring.personservice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import rba.onboaring.personservice.service.CryptoService;

@SpringBootTest
public class DigitalSignatureTests {

    @Autowired
    public CryptoService cryptoService;

    @Test
    public void signsCorrectly() {
        String plaintext = "123456789,TestName,TestSurname;";
        byte[] expectedCyphertext = {23, 115, -35, 62, 98, -9, 49, 82, -111, -84, 96, -74, 83, -13, 35, -50, 62, 41, -71, 71, -71, 113, -65, -35, -114, 51, 43, -2, 24, -88, -38, -70, 17, -116, 40, 26, 42, 53, -38, 14, 99, 115, 3, 95, -21, 43, 109, -46, -75, -71, 105, 100, 97, -21, -87, -81, -31, -66, 16, -6, -25, 96, 78, 59, -119, 72, -27, 29, 5, -105, 25, -16, -48, -112, 7, 27, 53, -45, 108, 11, 81, 20, -10, -36, 119, 43, 126, 9, -23, -102, 80, -38, 78, -102, 32, 118, 99, 46, 10, -46, 78, -75, 123, 92, 54, 117, 2, -39, 124, -76, 62, -58, -2, 83, -42, -23, 46, 97, -98, 83, -22, 46, 125, -85, 93, -60, -102, -106, 88, -83, -29, -32, 10, 83, -62, -115, 118, -5, 4, -111, -71, 124, -47, 64, -41, -92, -122, -17, -103, -65, 117, -12, -127, 66, 91, 35, -29, 16, 113, 43, -70, 1, -94, -1, -15, 79, 85, -67, -60, -104, -35, 20, -66, -94, 91, 90, 38, 27, 11, 80, 101, 117, -44, 70, 45, 107, 123, -17, -22, -31, 44, -38, -69, -56, 73, -84, -104, -50, 98, -31, -58, -101, -69, -39, 105, -120, 121, 37, -92, -65, -62, 65, 127, 49, -52, -111, 68, -10, -73, -51, 123, 126, 67, -3, -67, -125, 111, 59, 21, -79, 76, -39, -116, -55, 52, -34, 96, 23, 9, -65, 61, 80, -74, 109, -71, -30, -44, -21, 105, 68, -27, 41, -106, -36, -32, 28};
        byte[] receivedCyphertext = cryptoService.sign(plaintext);

        Assertions.assertEquals(expectedCyphertext, receivedCyphertext);
    }

}
