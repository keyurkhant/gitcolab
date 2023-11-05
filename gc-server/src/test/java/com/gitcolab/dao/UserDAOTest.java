package com.gitcolab.dao;

import com.gitcolab.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import static org.mockito.Mockito.*;

class UserDAOTest {

    private UserDAO userDAO;
    private User user;

    @BeforeEach
    void setUp() {
        userDAO = mock(UserDAO.class);
        user = mock(User.class);
    }

    @Test
    void testGetUserByUserName() {
        String username = "user123";
        when(userDAO.getUserByUserName(username)).thenReturn(Optional.of(user));

        Optional<User> result = userDAO.getUserByUserName(username);

        assertTrue(result.isPresent());
        assertEquals(user, result.get());

        verify(userDAO).getUserByUserName(username);
    }

    @Test
    void testGetUserByUserNameNotFound() {
        String username = "user123";
        when(userDAO.getUserByUserName(username)).thenReturn(Optional.empty());

        Optional<User> result = userDAO.getUserByUserName(username);

        assertFalse(result.isPresent());

        verify(userDAO).getUserByUserName(username);
    }

    @Test
    void testGetUserByEmail() {
        String email = "user@example.com";
        when(userDAO.getUserByEmail(email)).thenReturn(Optional.of(user));

        Optional<User> result = userDAO.getUserByEmail(email);

        assertTrue(result.isPresent());
        assertEquals(user, result.get());

        verify(userDAO).getUserByEmail(email);
    }

    @Test
    void testGetUserByEmailNotFound() {
        String email = "user@example.com";
        when(userDAO.getUserByEmail(email)).thenReturn(Optional.empty());

        Optional<User> result = userDAO.getUserByEmail(email);

        assertFalse(result.isPresent());

        verify(userDAO).getUserByEmail(email);
    }

    @Test
    void testExistsByUsername() {
        String username = "user123";
        when(userDAO.existsByUsername(username)).thenReturn(true);

        boolean result = userDAO.existsByUsername(username);

        assertTrue(result);

        verify(userDAO).existsByUsername(username);
    }

    @Test
    void testExistsByEmail() {
        String email = "user@example.com";
        when(userDAO.existsByEmail(email)).thenReturn(true);

        boolean result = userDAO.existsByEmail(email);

        assertTrue(result);

        verify(userDAO).existsByEmail(email);
    }


    //Test to updateProfile via DAO. - Uchenna
    @Test
    void testUpdateProfile(){
        String username = "user123";
        when(userDAO.updateProfile(username)).thenReturn(0);

        int result = userDAO.updateProfile(username);

        assertEquals(0, result);

        verify(userDAO).updateProfile(username);
    }
}