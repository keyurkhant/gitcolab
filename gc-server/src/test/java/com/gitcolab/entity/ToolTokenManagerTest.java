package com.gitcolab.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ToolTokenManagerTest {
    private ToolTokenManager integration;

    @BeforeEach
    public void setUp() {
        integration = ToolTokenManager.builder()
                .id(1L)
                .type(EnumIntegrationType.GITHUB)
                .token("token")
                .userId(3)
                .build();
    }

    @Test
    public void testIntegrationConstructorWithAllArgs() {
        ToolTokenManager integration1 = new ToolTokenManager(EnumIntegrationType.GITHUB, "token", 1);

        Assertions.assertEquals(EnumIntegrationType.GITHUB, integration1.getType());
        Assertions.assertEquals("token", integration1.getToken());
        Assertions.assertEquals(1, integration1.getUserId());
    }

    @Test
    public void testIntegrationConstructorWithToken() {
        EnumIntegrationType type = EnumIntegrationType.GITHUB;
        String token = "abc123";
        long userId = 12345;

        ToolTokenManager integration = new ToolTokenManager(type, token, userId);

        Assertions.assertEquals(integration.getType(), type);
        Assertions.assertEquals(integration.getToken(), token);
        Assertions.assertEquals(integration.getUserId(), userId);
    }

    @Test
    public void testIntegrationConstructorWithRepositoryId() {
        EnumIntegrationType type = EnumIntegrationType.ATLASSIAN;
        String repositoryId = "repo123";
        String token = "xyz789";
        long userId = 67890;

        ToolTokenManager integration = new ToolTokenManager(type, token, userId);

        Assertions.assertEquals(integration.getType(), type);
        Assertions.assertEquals(integration.getToken(), token);
        Assertions.assertEquals(integration.getUserId(), userId);
    }

    @Test
    public void testIntegrationBuilder() {
        EnumIntegrationType type = EnumIntegrationType.GITHUB;
        String repositoryId = "repo123";
        String token = "xyz789";
        long userId = 67890;

        ToolTokenManager integration = ToolTokenManager.builder()
                .type(type)
                .token(token)
                .userId(userId)
                .build();

        Assertions.assertEquals(integration.getType(), type);
        Assertions.assertEquals(integration.getToken(), token);
        Assertions.assertEquals(integration.getUserId(), userId);
    }
}
