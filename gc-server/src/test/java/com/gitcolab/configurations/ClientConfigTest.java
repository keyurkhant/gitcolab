package com.gitcolab.configurations;

import com.gitcolab.services.AtlassianServiceClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.reactive.function.client.WebClient;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class ClientConfigTest {

    @Autowired
    private ClientConfig clientConfig;

    @MockBean
    private WebClient webClient;

    @Test
    public void testAtlassianServiceClient() {
        // Ensure that the atlassianServiceClient bean is created
        AtlassianServiceClient atlassianServiceClient = clientConfig.atlassianServiceClient();
        assertNotNull(atlassianServiceClient);
    }
}