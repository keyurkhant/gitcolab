package com.gitcolab.utilities.mappers;

import com.gitcolab.entity.Project;
import com.gitcolab.utilities.mappers.ProjectRowMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProjectRowMapperTest {

    @Test
    public void testMapRow() throws SQLException {

        ResultSet resultSetMock = Mockito.mock(ResultSet.class);

        int id = 1;
        int userId = 42;
        Instant timestamp = Instant.now();
        String gitHubRepoName = "test-repo";
        String repositoryOwner = "test-repo-owner";
        String jiraBoardName = "jira";
        String atlassianProjectId = "100";

        Mockito.when(resultSetMock.getInt("id")).thenReturn(id);
        Mockito.when(resultSetMock.getInt("userId")).thenReturn(userId);
        Mockito.when(resultSetMock.getTimestamp("timestamp")).thenReturn(Timestamp.from(timestamp));
        Mockito.when(resultSetMock.getString("repositoryName")).thenReturn(gitHubRepoName);
        Mockito.when(resultSetMock.getString("repositoryOwner")).thenReturn(repositoryOwner);
        Mockito.when(resultSetMock.getString("atlassianProjectId")).thenReturn(atlassianProjectId);
        Mockito.when(resultSetMock.getString("jiraBoardName")).thenReturn(jiraBoardName);

        ProjectRowMapper projectRowMapper = new ProjectRowMapper();
        Project mappedProject = projectRowMapper.mapRow(resultSetMock, 1);


        assertEquals(id, mappedProject.getId());
        assertEquals(userId, mappedProject.getUserId());
        assertEquals(timestamp, mappedProject.getTimestamp());
        assertEquals(atlassianProjectId, mappedProject.getAtlassianProjectId());
    }
}