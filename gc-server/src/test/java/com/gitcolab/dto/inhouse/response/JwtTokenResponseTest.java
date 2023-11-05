package com.gitcolab.dto.inhouse.response;

import com.gitcolab.dto.inhouse.response.JwtTokenResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class JwtTokenResponseTest {

    @Test
    void testConstructorAndGetters() {
        String token = "access_token";
        String type = "Bearer";
        String refreshToken = "refresh_token";
        Long id = 1L;
        String username = "test_user";
        String email = "test@example.com";
        List<String> roles = Arrays.asList("ROLE_ADMIN", "ROLE_USER");

        JwtTokenResponse response = new JwtTokenResponse(token, type, refreshToken, id, username, email, roles);

        Assertions.assertEquals(token, response.getToken());
        Assertions.assertEquals(type, response.getType());
        Assertions.assertEquals(refreshToken, response.getRefreshToken());
        Assertions.assertEquals(id, response.getId());
        Assertions.assertEquals(username, response.getUsername());
        Assertions.assertEquals(email, response.getEmail());
        Assertions.assertEquals(roles, response.getRoles());
    }

    @Test
    void testAlternateConstructorAndGetters() {
        String token = "access_token";
        String refreshToken = "refresh_token";
        Long id = 1L;
        String username = "test_user";
        String email = "test@example.com";

        JwtTokenResponse response = new JwtTokenResponse(token, refreshToken, id, username, email);

        Assertions.assertEquals(token, response.getToken());
        Assertions.assertEquals("Bearer", response.getType());
        Assertions.assertEquals(refreshToken, response.getRefreshToken());
        Assertions.assertEquals(id, response.getId());
        Assertions.assertEquals(username, response.getUsername());
        Assertions.assertEquals(email, response.getEmail());
        Assertions.assertNull(response.getRoles());
    }

    @Test
    void testToString() {
        String token = "access_token";
        String type = "Bearer";
        String refreshToken = "refresh_token";
        Long id = 1L;
        String username = "test_user";
        String email = "test@example.com";
        List<String> roles = Arrays.asList("ROLE_ADMIN", "ROLE_USER");

        JwtTokenResponse response = new JwtTokenResponse(token, type, refreshToken, id, username, email, roles);

        String expectedToString = "JwtTokenResponse(token=access_token, type=Bearer, refreshToken=refresh_token, id=1, username=test_user, email=test@example.com, roles=[ROLE_ADMIN, ROLE_USER])";
        Assertions.assertEquals(expectedToString, response.toString());
    }
}
