package com.gitcolab.entity;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProjectTest {
    private Project project;

    @Before
    public void setUp() {
        // You can create a sample Project object for testing
        int userId = 1;
        String repositoryName = "sample-repo";
        String repositoryOwner = "sample-owner";
        String jiraBoardName = "board-456";
        Instant timestamp = Instant.now();

        project = new Project(userId, repositoryName, repositoryOwner, jiraBoardName, timestamp);
    }

    @Test
    public void testParameterizedConstructor() {
        // Test the parameterized constructor
        int userId = 1;
        String repositoryName = "sample-repo";
        String repositoryOwner = "sample-owner";
        String jiraBoardName = "board-456";
        Instant timestamp = Instant.now();

        Project project = new Project(userId, repositoryName, repositoryOwner, jiraBoardName, timestamp);

        assertEquals(userId, project.getUserId());
        assertEquals(repositoryName, project.getRepositoryName());
        assertEquals(repositoryOwner, project.getRepositoryOwner());
        assertEquals(jiraBoardName, project.getJiraBoardName());
        assertEquals(timestamp, project.getTimestamp());
    }

    @Test
    public void testDefaultConstructor() {
        // Test the default constructor
        Project project = new Project();

        // Verify that all fields are initialized with default values
        assertEquals(0, project.getId());
        assertEquals(0, project.getUserId());
        assertEquals(null, project.getRepositoryName());
        assertEquals(null, project.getRepositoryOwner());
        assertEquals(null, project.getAtlassianProjectId());
        assertEquals(null, project.getJiraBoardName());
        assertEquals(null, project.getTimestamp());
    }

    @Test
    public void testGettersAndSetters() {
        // Test the getters and setters
        int id = 100;
        int userId = 2;
        String repositoryName = "new-repo";
        String repositoryOwner = "new-owner";
        String atlassianProjectId = "project-789";
        String jiraBoardName = "board-012";
        Instant timestamp = Instant.now().plusSeconds(3600);

        Project project = new Project();
        // Set new values using setters
        project.setId(id);
        project.setUserId(userId);
        project.setRepositoryName(repositoryName);
        project.setRepositoryOwner(repositoryOwner);
        project.setAtlassianProjectId(atlassianProjectId);
        project.setJiraBoardName(jiraBoardName);
        project.setTimestamp(timestamp);

        // Verify that getters return the updated values
        assertEquals(id, project.getId());
        assertEquals(userId, project.getUserId());
        assertEquals(repositoryName, project.getRepositoryName());
        assertEquals(repositoryOwner, project.getRepositoryOwner());
        assertEquals(atlassianProjectId, project.getAtlassianProjectId());
        assertEquals(jiraBoardName, project.getJiraBoardName());
        assertEquals(timestamp, project.getTimestamp());
    }
}