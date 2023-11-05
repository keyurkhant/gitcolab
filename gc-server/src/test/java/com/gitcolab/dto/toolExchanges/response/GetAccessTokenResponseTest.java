package com.gitcolab.dto.toolExchanges.response;



import com.gitcolab.dto.toolExchanges.response.GetAccessTokenResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GetAccessTokenResponseTest {
    
    @Test
    public void testGettersAndSetters() {
        GetAccessTokenResponse response = new GetAccessTokenResponse();

        
        response.setAccess_token("sample_access_token");
        response.setExpires_in("3600");
        response.setScope("read write");

        
        assertEquals("sample_access_token", response.getAccess_token());
        assertEquals("3600", response.getExpires_in());
        assertEquals("read write", response.getScope());
    }

    @Test
    public void testNoArgsConstructor() {
        GetAccessTokenResponse response = new GetAccessTokenResponse();

        
        assertNull(response.getAccess_token());
        assertNull(response.getExpires_in());
        assertNull(response.getScope());
    }

    @Test
    public void testAllArgsConstructor() {
        
        GetAccessTokenResponse response = new GetAccessTokenResponse("sample_access_token", "3600", "read write");

        
        assertEquals("sample_access_token", response.getAccess_token());
        assertEquals("3600", response.getExpires_in());
        assertEquals("read write", response.getScope());
    }

    @Test
    public void testToString() {
        GetAccessTokenResponse response = new GetAccessTokenResponse("sample_access_token", "3600", "read write");

        
        assertEquals("GetAccessTokenResponse(access_token=sample_access_token, expires_in=3600, scope=read write)",
                response.toString());
    }
    
}