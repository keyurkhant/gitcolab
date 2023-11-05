package com.gitcolab.services;

import com.gitcolab.dto.UserInfo;
import com.gitcolab.entity.User;
import com.gitcolab.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ExploreServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ExploreService exploreService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoadUserByUsername() {
        String username = "user";
        User user = new User();
        user.setUsername(username);
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        UserInfo userInfo = exploreService.loadUserByUsername(username);
        assertNotNull(userInfo);
        assertEquals(username, userInfo.getUsername());
    }

    @Test
    void testLoadUserByUsername_NotFound() {
        String username = "user";
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> {
            exploreService.loadUserByUsername(username);
        });
    }

    @Test
    void testGetConnections() {
        String username = "1";
        User user1 = new User();
        user1.setId(1L);
        user1.setUsername("user1");
        User user2 = new User();
        user2.setId(2L);
        user2.setUsername("user2");
        User user3 = new User();
        user3.setId(3L);
        user3.setUsername("user3");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));
        when(userRepository.findById(2L)).thenReturn(Optional.of(user2));
        when(userRepository.findById(3L)).thenReturn(Optional.of(user3));
        List<UserInfo> connections = exploreService.getConnections(username);
        assertEquals(3, connections.size());
        assertEquals("user1", connections.get(0).getUsername());
        assertEquals("user2", connections.get(1).getUsername());
        assertEquals("user3", connections.get(2).getUsername());
    }
}
