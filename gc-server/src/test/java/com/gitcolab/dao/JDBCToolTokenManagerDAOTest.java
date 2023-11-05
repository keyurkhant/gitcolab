package com.gitcolab.dao;

import com.gitcolab.entity.EnumIntegrationType;
import com.gitcolab.entity.ToolTokenManager;
import com.gitcolab.utilities.mappers.IntegrationRowMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@SpringBootTest
class JDBCToolTokenManagerDAOTest {

    @InjectMocks
    private JDBCToolTokenManagerDAO jdbcIntegrationDAO;

    @Mock
    private JdbcTemplate jdbcTemplate;
    @Mock
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    @Test
    public void saveTest(){
        ToolTokenManager integration = new ToolTokenManager();
        integration.setType(EnumIntegrationType.GITHUB);
        integration.setToken("some-token");
        integration.setUserId(456L);

        when(namedParameterJdbcTemplate.update(anyString(), Mockito.any(SqlParameterSource.class)))
                .thenReturn(1);

        int result = jdbcIntegrationDAO.save(integration);

        assertEquals(1, result);
    }

    @Test
    public void testGetByEmail() {
        String email = "test@example.com";
        EnumIntegrationType integrationType = EnumIntegrationType.GITHUB;
        ToolTokenManager integration = new ToolTokenManager();
        integration.setType(integrationType);

        when(jdbcTemplate.queryForObject(anyString(), Mockito.any(Object[].class), Mockito.any(IntegrationRowMapper.class)))
                .thenReturn(integration);

        Optional<ToolTokenManager> result = jdbcIntegrationDAO.getByEmail(email, integrationType);

        assertTrue(result.isPresent());
        assertEquals(integrationType, result.get().getType());
    }

    @Test
    public void testGetByEmail_NonExistingIntegration() {
        String email = "test@example.com";
        EnumIntegrationType integrationType = EnumIntegrationType.GITHUB;

        when(jdbcTemplate.queryForObject(anyString(), Mockito.any(Object[].class), Mockito.any(IntegrationRowMapper.class)))
                .thenThrow(EmptyResultDataAccessException.class);

        Optional<ToolTokenManager> result = jdbcIntegrationDAO.getByEmail(email, integrationType);

        assertFalse(result.isPresent());
    }

    @Test
    public void testUpdate() {
        ToolTokenManager integration = new ToolTokenManager();
        integration.setType(EnumIntegrationType.GITHUB);
        integration.setToken("updated-token");
        integration.setUserId(456L);

        when(namedParameterJdbcTemplate.update(anyString(), Mockito.any(SqlParameterSource.class)))
                .thenReturn(1);

        int result = jdbcIntegrationDAO.update(integration);

        assertEquals(1, result);
        Mockito.verify(namedParameterJdbcTemplate, times(1)).update(anyString(), Mockito.any(SqlParameterSource.class));
    }

    @Test
    public void testGet() {
        long id = 1L;
        ToolTokenManager integration = new ToolTokenManager();
        integration.setId(id);

        when(jdbcTemplate.queryForObject(anyString(), Mockito.any(Object[].class), Mockito.any(IntegrationRowMapper.class)))
                .thenReturn(integration);

        Optional<ToolTokenManager> result = jdbcIntegrationDAO.get(id);

        assertTrue(result.isPresent());
        assertEquals(id, result.get().getId());
    }

    @Test
    public void testGet_NonExistingIntegration() {
        long id = 1L;
        when(jdbcTemplate.queryForObject(anyString(), Mockito.any(Object[].class), Mockito.any(IntegrationRowMapper.class)))
                .thenThrow(EmptyResultDataAccessException.class);

        assertThrows(EmptyResultDataAccessException.class,()->{
            jdbcIntegrationDAO.get(id);
        });

    }
}