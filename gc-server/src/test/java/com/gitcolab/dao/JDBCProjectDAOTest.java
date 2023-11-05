package com.gitcolab.dao;

import static org.junit.jupiter.api.Assertions.*;

import com.gitcolab.entity.Project;
import com.gitcolab.utilities.mappers.ProjectRowMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest

public class JDBCProjectDAOTest {

    @Mock
    private NamedParameterJdbcTemplate namedParameterJdbcTemplateMock;

    @Mock
    private JdbcTemplate jdbcTemplateMock;

    @InjectMocks
    private JDBCProjectDAO jdbcProjectDAO;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGet() {
        int projectId = 1;
        Project expectedProject = new Project();
        expectedProject.setId(projectId);

        
        when(jdbcTemplateMock.queryForObject(anyString(), any(Object[].class),any(ProjectRowMapper.class)))
                .thenReturn(expectedProject);

        Optional<Project> result = jdbcProjectDAO.get(projectId);

        
        assertTrue(result.isPresent());
        assertEquals(expectedProject.getId(), result.get().getId());

        
        verify(jdbcTemplateMock).queryForObject(eq("select * from Project p where p.id=?"), any(Object[].class), any(ProjectRowMapper.class));
    }

    @Test
    public void testSave() {
        Project projectToSave = new Project();
        projectToSave.setUserId(42);

        
        when(namedParameterJdbcTemplateMock.update(anyString(),any(BeanPropertySqlParameterSource.class)))
                .thenReturn(1); 

        int result = jdbcProjectDAO.save(projectToSave);

        
        assertEquals(1, result);

        
        verify(namedParameterJdbcTemplateMock).update(
                eq("INSERT INTO Project(`userId`,`repositoryName`,`repositoryOwner`,`atlassianProjectId`,`jiraBoardName`,`timestamp`) " +
                        "values(:userId,:repositoryName,:repositoryOwner,:atlassianProjectId,:jiraBoardName,:timestamp)"),
                any(BeanPropertySqlParameterSource.class)
        );
    }

    @Test
    public void testUpdate() {
        Project projectToUpdate = new Project();
        projectToUpdate.setId(1);
        projectToUpdate.setUserId(42);

        
        when(namedParameterJdbcTemplateMock.update(anyString(), any(BeanPropertySqlParameterSource.class)))
                .thenReturn(1); 

        int result = jdbcProjectDAO.update(projectToUpdate);

        
        assertEquals(1, result);

        
        verify(namedParameterJdbcTemplateMock).update(
                eq("UPDATE Project SET `userId`=:userId,`repositoryName`=:repositoryName,`repositoryOwner`=:repositoryOwner,`atlassianProjectId`=:atlassianProjectId, `jiraBoardName`=:jiraBoardName, `timestamp`=:timestamp WHERE `id` = :id"),
                any(BeanPropertySqlParameterSource.class)
        );
    }

    @Test
    public void testDelete() {

    }
}
