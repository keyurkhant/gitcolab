package com.gitcolab.dto.toolExchanges.response;

import com.gitcolab.dto.toolExchanges.response.ProjectResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ProjectResponseTest {

    @Test
    public void testProjectResponseNoArgsConstructor() {
        
        ProjectResponse projectResponse = new ProjectResponse();

        
        
        assertEquals(null, projectResponse.getSelf());
        assertEquals(null, projectResponse.getId());
        assertEquals(null, projectResponse.getKey());
        assertEquals(null, projectResponse.getName());
    }

    @Test
    public void testProjectResponseAllArgsConstructor() {
        
        String self = "http://example.com/projects/sample";
        String id = "P123";
        String key = "SAMPLE";
        String name = "Sample Project";

        
        ProjectResponse projectResponse = new ProjectResponse(self, id, key, name);

        
        
        assertEquals(self, projectResponse.getSelf());
        assertEquals(id, projectResponse.getId());
        assertEquals(key, projectResponse.getKey());
        assertEquals(name, projectResponse.getName());
    }

    @Test
    public void testProjectResponseFieldValues() {
        
        String self = "http://example.com/projects/sample";
        String id = "P123";
        String key = "SAMPLE";
        String name = "Sample Project";

        
        ProjectResponse projectResponse = new ProjectResponse(self, id, key, name);

        
        assertEquals(self, projectResponse.getSelf());
        assertEquals(id, projectResponse.getId());
        assertEquals(key, projectResponse.getKey());
        assertEquals(name, projectResponse.getName());
    }
}
