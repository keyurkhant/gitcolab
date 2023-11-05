package com.gitcolab.entity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.Instant;

class RefreshTokenTest {
    private RefreshToken refreshToken;

    @BeforeEach
    void setUp() {
        long id = 1;
        User user = new User();
        String token = "abcdef123456";
        Instant expiryDate = Instant.now();

        refreshToken = new RefreshToken(id, user, token, expiryDate);
    }

    @AfterEach
    void tearDown() {
        refreshToken = null;
    }

    @Test
    void testConstructorAndGetters() {
        Assertions.assertEquals(1, refreshToken.getId());
        Assertions.assertNotNull(refreshToken.getUser());
        Assertions.assertEquals("abcdef123456", refreshToken.getToken());
        Assertions.assertNotNull(refreshToken.getExpiryDate());
    }

    @Test
    void testSetters() {
        long id = 2;
        User user = new User();
        String token = "xyz789";
        Instant expiryDate = Instant.now().plusSeconds(3600);

        refreshToken.setId(id);
        refreshToken.setUser(user);
        refreshToken.setToken(token);
        refreshToken.setExpiryDate(expiryDate);

        Assertions.assertEquals(2, refreshToken.getId());
        Assertions.assertEquals(user, refreshToken.getUser());
        Assertions.assertEquals("xyz789", refreshToken.getToken());
        Assertions.assertEquals(expiryDate, refreshToken.getExpiryDate());
    }
}
