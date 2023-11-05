package com.gitcolab.repositories;

import com.gitcolab.dao.UserDAO;
import com.gitcolab.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserRepositoryImplementationTest {

    @Mock
    private UserDAO userDao;

    @InjectMocks
    private UserRepositoryImplementation userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save_ShouldReturnSavedUser() {
        // Arrange
        User user = new User();
        user.setId(1);
        when(userDao.save(user)).thenReturn(1);

        // Act
        int savedId = userRepository.save(user);

        // Assert
        assertEquals(1, savedId);
        verify(userDao, times(1)).save(user);
    }

    @Test
    void existsByUsername_ShouldReturnTrue_WhenUsernameExists() {
        // Arrange
        String username = "user";
        when(userDao.existsByUsername(username)).thenReturn(true);

        // Act
        boolean exists = userRepository.existsByUsername(username);

        // Assert
        assertTrue(exists);
        verify(userDao, times(1)).existsByUsername(username);
    }

    @Test
    void existsByUsername_ShouldReturnFalse_WhenUsernameDoesNotExist() {
        // Arrange
        String username = "user";
        when(userDao.existsByUsername(username)).thenReturn(false);

        // Act
        boolean exists = userRepository.existsByUsername(username);

        // Assert
        assertFalse(exists);
        verify(userDao, times(1)).existsByUsername(username);
    }

    //UserRepositoryTest - uchenna
    @Test
    void updateProfile_ShouldReturnZero_WhenUpdated(){
        // Arrange
        User user = new User();
        when(userDao.updateProfile(user)).thenReturn(0);

        // Act
        int returnVal = userRepository.updateProfile(user);

        // Assert
        assertEquals(0, returnVal);

        //verify
        verify(userDao, times(1)).updateProfile(user);
    }

}
