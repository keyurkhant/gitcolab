package com.gitcolab.services;

import com.gitcolab.entity.User;
import com.gitcolab.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserDetailsServiceImplTest {

    private UserDetailsServiceImpl userDetailsService;
    private UserRepository userRepositoryMock;

    @BeforeEach
    void setUp() {
        userRepositoryMock = mock(UserRepository.class);
        userDetailsService = new UserDetailsServiceImpl(userRepositoryMock);
//        userDetailsService.setUserRepository(userRepositoryMock);
    }

    @Test
    void testLoadUserByUsername_ExistingUser() {
        String username = "user";
        User user = new User();
        user.setUsername(username);

        // Mock the UserRepository behavior
        when(userRepositoryMock.findByUsername(username)).thenReturn(Optional.of(user));

        // Call the loadUserByUsername() method
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        // Verify that the UserDetailsImpl instance is returned
        assertNotNull(userDetails);
        assertEquals(username, userDetails.getUsername());
    }

    @Test
    void testLoadUserByUsername_NonExistingUser() {
        String username = "user";

        // Mock the UserRepository behavior
        when(userRepositoryMock.findByUsername(username)).thenReturn(Optional.empty());

        // Call the loadUserByUsername() method and verify that a UsernameNotFoundException is thrown
        assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername(username);
        });
    }

    @Test
    void testLoadUserByUsername_NullUsername() {
        assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername(null);
        });
    }

    @Test
    void testLoadUserByUsername_EmptyUsername() {
        assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername("");
        });
    }

}
