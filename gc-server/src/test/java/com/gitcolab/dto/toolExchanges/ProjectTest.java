package com.gitcolab.dto.toolExchanges;

import com.gitcolab.dto.toolExchanges.Project;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProjectTest {

    @Test
    public void testAllArgsConstructor() {
        
        String projectId = "12345";

        
        Project project = new Project(projectId);

        
        assertEquals(projectId, project.getId());
    }

    @Test
    public void testNoArgsConstructor() {
        
        Project project = new Project();

        
        assertEquals(null, project.getId());
    }

    @Test
    public void testSetAndGetId() {
        
        Project project = new Project();
        String projectId = "54321";

        
        project.setId(projectId);

        
        assertEquals(projectId, project.getId());
    }
}