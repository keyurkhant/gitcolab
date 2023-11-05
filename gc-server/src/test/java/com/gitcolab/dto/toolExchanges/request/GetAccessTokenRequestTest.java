package com.gitcolab.dto.toolExchanges.request;

import com.gitcolab.dto.toolExchanges.request.GetAccessTokenRequest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GetAccessTokenRequestTest {

    @Test
    public void testGettersAndSetters() {
        GetAccessTokenRequest request = new GetAccessTokenRequest();

        
        request.setGrant_type("authorization_code");
        request.setClient_id("client_id");
        request.setClient_secret("client_secret");
        request.setCode("code");
        request.setRedirect_uri("redirect_uri");

        
        assertEquals("authorization_code", request.getGrant_type());
        assertEquals("client_id", request.getClient_id());
        assertEquals("client_secret", request.getClient_secret());
        assertEquals("code", request.getCode());
        assertEquals("redirect_uri", request.getRedirect_uri());
    }

    @Test
    public void testNoArgsConstructor() {
        GetAccessTokenRequest request = new GetAccessTokenRequest();

        
        assertNull(request.getGrant_type());
        assertNull(request.getClient_id());
        assertNull(request.getClient_secret());
        assertNull(request.getCode());
        assertNull(request.getRedirect_uri());
    }

    @Test
    public void testAllArgsConstructor() {
        
        GetAccessTokenRequest request = new GetAccessTokenRequest("authorization_code", "client_id", "client_secret", "code", "redirect_uri");

        
        assertEquals("authorization_code", request.getGrant_type());
        assertEquals("client_id", request.getClient_id());
        assertEquals("client_secret", request.getClient_secret());
        assertEquals("code", request.getCode());
        assertEquals("redirect_uri", request.getRedirect_uri());
    }

    @Test
    public void testToString() {
        GetAccessTokenRequest request = new GetAccessTokenRequest("authorization_code", "client_id", "client_secret", "code", "redirect_uri");

        
        assertEquals("GetAccessTokenRequest(grant_type=authorization_code, client_id=client_id, client_secret=client_secret, code=code, redirect_uri=redirect_uri)",
                request.toString());
    }
}