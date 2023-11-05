package com.gitcolab.dto.inhouse.response;

import com.gitcolab.dto.inhouse.response.TokenRefreshResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TokenRefreshResponseTest {

    @Test
    void testNoArgsConstructor() {
        TokenRefreshResponse response = new TokenRefreshResponse();

        Assertions.assertNull(response.getAccessToken());
        Assertions.assertNull(response.getRefreshToken());
        Assertions.assertEquals("Bearer", response.getTokenType());
    }

    @Test
    void testAllArgsConstructor() {
        String accessToken = "sampleAccessToken";
        String refreshToken = "sampleRefreshToken";
        String tokenType = "CustomType";

        TokenRefreshResponse response = new TokenRefreshResponse(accessToken, refreshToken, tokenType);

        Assertions.assertEquals(accessToken, response.getAccessToken());
        Assertions.assertEquals(refreshToken, response.getRefreshToken());
        Assertions.assertEquals(tokenType, response.getTokenType());
    }

    @Test
    void testCustomConstructor() {
        String accessToken = "sampleAccessToken";
        String refreshToken = "sampleRefreshToken";

        TokenRefreshResponse response = new TokenRefreshResponse(accessToken, refreshToken);

        Assertions.assertEquals(accessToken, response.getAccessToken());
        Assertions.assertEquals(refreshToken, response.getRefreshToken());
        Assertions.assertEquals("Bearer", response.getTokenType());
    }

    // Add additional test cases if needed
}
