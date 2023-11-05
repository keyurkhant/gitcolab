package com.gitcolab.services;

import com.gitcolab.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserDetailsImplTest {

    private UserDetailsImpl userDetails;
    private User userMock;

    @BeforeEach
    void setUp() {
        userMock = mock(User.class);
    }

    @Test
    void testBuild() {
        // Mock the User object
        when(userMock.getId()).thenReturn(1L);
        when(userMock.getUsername()).thenReturn("user");
        when(userMock.getEmail()).thenReturn("user@example.com");
        when(userMock.getPassword()).thenReturn("password");

        userDetails = UserDetailsImpl.build(userMock);

        // Verify the UserDetailsImpl instance is created correctly
        assertEquals(1L, userDetails.getId());
        assertEquals("user", userDetails.getUsername());
        assertEquals("user@example.com", userDetails.getEmail());
        assertEquals("password", userDetails.getPassword());
        assertEquals(new ArrayList<>(),userDetails.getAuthorities());
    }

    @Test
    void testGetterMethods() {
        userDetails = new UserDetailsImpl(1L, "user", "user@example.com", "password", null);

        assertEquals(1L, userDetails.getId());
        assertEquals("user", userDetails.getUsername());
        assertEquals("user@example.com", userDetails.getEmail());
        assertEquals("password", userDetails.getPassword());
    }

    @Test
    void testBooleanMethods() {
        userDetails = new UserDetailsImpl(1L, "user", "user@example.com", "password", null);

        assertEquals(true, userDetails.isAccountNonExpired());
        assertEquals(true, userDetails.isAccountNonLocked());
        assertEquals(true, userDetails.isCredentialsNonExpired());
        assertEquals(true, userDetails.isEnabled());
    }

    @Test
    void testGetAuthorities() {
        // Mock the GrantedAuthority objects
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        userDetails = new UserDetailsImpl(1L, "user", "user@example.com", "password", authorities);

        assertEquals(authorities, userDetails.getAuthorities());
    }

    @Test
    void testBuild_NullUser() {
        assertThrows(NullPointerException.class, () -> UserDetailsImpl.build(null));
    }

    @Test
    void testGetAuthorities_NullAuthorities() {
        userDetails = new UserDetailsImpl(1L, "user", "user@example.com", "password", null);
        assertNull(userDetails.getAuthorities());
    }

}
