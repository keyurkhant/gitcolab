package com.gitcolab.dao;

import com.gitcolab.entity.User;
import com.gitcolab.utilities.mappers.UserRowMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class JDBCUserDAOTest {

    private JDBCUserDAO userDAO;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        namedParameterJdbcTemplate = mock(NamedParameterJdbcTemplate.class);
        jdbcTemplate = mock(JdbcTemplate.class);
        userDAO = new JDBCUserDAO();
        userDAO.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        userDAO.jdbcTemplate = jdbcTemplate;
    }

    @Test
    void testGet() {
        long id = 1L;
        User user = new User();
        user.setId(id);
        when(jdbcTemplate.queryForObject(anyString(), any(Object[].class), any(UserRowMapper.class))).thenReturn(user);

        Optional<User> result = userDAO.get(id);

        assertTrue(result.isPresent());
        assertEquals(user, result.get());

        verify(jdbcTemplate).queryForObject(anyString(), any(Object[].class), any(UserRowMapper.class));
    }

    @Test
    void testSave() {
        User user = new User();
        int expectedRowCount = 1;
        when(namedParameterJdbcTemplate.update(anyString(), any(SqlParameterSource.class))).thenReturn(expectedRowCount);

        int rowCount = userDAO.save(user);

        assertEquals(expectedRowCount, rowCount);

        verify(namedParameterJdbcTemplate).update(anyString(), any(SqlParameterSource.class));
    }


    @Test
    void testUpdate() {
//        User user = new User();
//        int expectedRowCount = 0;
//        when(namedParameterJdbcTemplate.update(anyString(),any(SqlParameterSource.class))).thenReturn(expectedRowCount);
//
//        int rowCount = userDAO.update(user);
//
//        assertEquals(expectedRowCount, rowCount);
//
//        verify(namedParameterJdbcTemplate).update(anyString(), any(SqlParameterSource.class));
    }

    @Test
    void testDelete() {
//        long id = 1L;
//        when(jdbcTemplate.update(anyString(),any(Object[].class))).thenReturn(1);
//
//        userDAO.delete(id);

//        verify(jdbcTemplate).update(anyString(), any(Object[].class));
    }


    @Test
    void testGetUserByUsername() {
        String username = "user123";
        User user = new User();
        user.setUsername(username);
        when(jdbcTemplate.queryForObject(anyString(), any(Object[].class), any(UserRowMapper.class))).thenReturn(user);

        Optional<User> result = userDAO.getUserByUserName(username);

        assertTrue(result.isPresent());
        assertEquals(user, result.get());

        verify(jdbcTemplate).queryForObject(anyString(), any(Object[].class), any(UserRowMapper.class));
    }

    @Test
    void testGetUserByEmail() {
        String email = "user@example.com";
        User user = new User();
        user.setEmail(email);
        when(jdbcTemplate.queryForObject(anyString(), any(Object[].class), any(UserRowMapper.class))).thenReturn(user);

        Optional<User> result = userDAO.getUserByEmail(email);

        assertTrue(result.isPresent());
        assertEquals(user, result.get());

        verify(jdbcTemplate).queryForObject(anyString(), any(Object[].class), any(UserRowMapper.class));
    }

    @Test
    void testExistsByUsername() {
        String username = "user123";
        int expectedCount = 1;
        when(jdbcTemplate.queryForObject(anyString(), any(Object[].class), any(Class.class))).thenReturn(expectedCount);

        boolean result = userDAO.existsByUsername(username);

        assertTrue(result);

        verify(jdbcTemplate).queryForObject(anyString(), any(Object[].class), any(Class.class));
    }

    @Test
    void testExistsByEmail() {
        String email = "user@example.com";
        int expectedCount = 1;
        when(jdbcTemplate.queryForObject(anyString(), any(Object[].class), any(Class.class))).thenReturn(expectedCount);

        boolean result = userDAO.existsByEmail(email);

        assertTrue(result);

        verify(jdbcTemplate).queryForObject(anyString(), any(Object[].class), any(Class.class));
    }

    @Test
    void testUpdateProfile(){
        //arrange
        User user = new User();
        int expected = 0;

        //act
        when(namedParameterJdbcTemplate.update(anyString(),any(SqlParameterSource.class))).thenReturn(expected);
        int result = userDAO.updateProfile(user);

        //assert
        assertEquals(expected, result);

        //verify
        verify(namedParameterJdbcTemplate, times(1)).update(anyString(), any(SqlParameterSource.class));
    }

}
