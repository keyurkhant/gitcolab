package com.gitcolab.dto.toolExchanges.response;

import com.gitcolab.dto.toolExchanges.response.AccessibleResource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AccessibleResourceTest {

    @Test
    public void testConstructorAndGetters() {
        
        String id = "123";
        String url = "https://example.com/resource";
        String name = "Test Resource";
        String avatarUrl = "https://example.com/avatar";

        
        AccessibleResource resource = new AccessibleResource(id, url, name, avatarUrl);

        
        assertEquals(id, resource.getId());
        assertEquals(url, resource.getUrl());
        assertEquals(name, resource.getName());
        assertEquals(avatarUrl, resource.getAvatarUrl());
    }

    @Test
    public void testEquality() {
        
        AccessibleResource resource1 = new AccessibleResource("1", "url1", "Resource 1", "avatar1");
        AccessibleResource resource2 = new AccessibleResource("1", "url2", "Resource 2", "avatar2");
        AccessibleResource resource3 = new AccessibleResource("2", "url1", "Resource 1", "avatar1");

        
        assertEquals(resource1, resource2); // Two resources with the same ID are considered equal
        assertNotEquals(resource1, resource3); // Two resources with different IDs are not equal
        assertNotEquals(resource2, resource3); // Two resources with different IDs are not equal
    }


    @Test
    public void testSetId() {
        
        AccessibleResource resource = new AccessibleResource();

        
        resource.setId("12345");

        
        assertEquals("12345", resource.getId());
    }

    @Test
    public void testSetUrl() {
        
        AccessibleResource resource = new AccessibleResource(null, null, null, null);

        
        resource.setUrl("https://example.com/resource");

        
        assertEquals("https://example.com/resource", resource.getUrl());
    }

    @Test
    public void testSetName() {
        
        AccessibleResource resource = new AccessibleResource(null, null, null, null);

        
        resource.setName("Resource Name");

        
        assertEquals("Resource Name", resource.getName());
    }

    @Test
    public void testSetAvatarUrl() {
        
        AccessibleResource resource = new AccessibleResource(null, null, null, null);

        
        resource.setAvatarUrl("https://example.com/avatar.png");

        
        assertEquals("https://example.com/avatar.png", resource.getAvatarUrl());
    }
}