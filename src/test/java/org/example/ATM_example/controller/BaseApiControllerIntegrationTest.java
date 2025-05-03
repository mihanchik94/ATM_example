package org.example.ATM_example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.ATM_example.PostgresSQLTestContainerExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(PostgresSQLTestContainerExtension.class)
public class BaseApiControllerIntegrationTest {
    @Autowired
    protected TestRestTemplate restTemplate;

    @Autowired
    protected ObjectMapper objectMapper;

}
