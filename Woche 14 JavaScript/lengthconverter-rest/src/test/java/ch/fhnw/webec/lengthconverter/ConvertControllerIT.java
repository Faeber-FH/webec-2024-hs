package ch.fhnw.webec.lengthconverter;

import ch.fhnw.webec.lengthconverter.ConvertController.ConvertResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.client.RestClient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class ConvertControllerIT {

    @LocalServerPort
    int port;

    RestClient client;

    @BeforeEach
    public void setup() {
        client = RestClient.create("http://localhost:" + port + "/api/");
    }

    @Test
    public void convert() {
        var response = client.get().uri("convert?feet=5&inches=8")
                .retrieve().body(ConvertResponse.class);
        assertNotNull(response);
        assertEquals(172, response.cmPart());
        assertEquals(7, response.mmPart());
    }
}
