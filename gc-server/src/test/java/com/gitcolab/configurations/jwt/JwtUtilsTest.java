package com.gitcolab.configurations.jwt;

import com.gitcolab.services.UserDetailsImpl;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class JwtUtilsTest {
//
//    private JwtUtils jwtUtils;
//
//    @BeforeEach
//    void setUp() {
//        jwtUtils = new JwtUtils();
////        jwtUtils.jwtSecret = "secretKey";
////        jwtUtils.jwtExpirationMs = 3600000; // 1 hour
//    }
//
//    @Test
//    void testGenerateJwtToken() {
//        UserDetailsImpl userPrincipal = mock(UserDetailsImpl.class);
//        when(userPrincipal.getUsername()).thenReturn("testuser");
//
//        String jwtToken = jwtUtils.generateJwtToken(userPrincipal);
//
//        assertNotNull(jwtToken);
//        assertTrue(jwtToken.length() > 0);
//    }
//
//    @Test
//    void testGetUserNameFromJwtToken_ValidToken() {
//        String jwtToken = "validJwtToken";
//
//        String username = jwtUtils.getUserNameFromJwtToken(jwtToken);
//
//        assertEquals("testuser", username);
//    }
//
//    @Test
//    void testGetUserNameFromJwtToken_InvalidToken() {
//        String jwtToken = "invalidJwtToken";
//
//        assertThrows(MalformedJwtException.class, () -> {
//            jwtUtils.getUserNameFromJwtToken(jwtToken);
//        });
//    }
//
//    @Test
//    void testValidateJwtToken_ValidToken() {
//        String jwtToken = "validJwtToken";
//
//        boolean isValid = jwtUtils.validateJwtToken(jwtToken);
//
//        assertTrue(isValid);
//    }
//
//    @Test
//    void testValidateJwtToken_ExpiredToken() {
//        String expiredJwtToken = "expiredJwtToken";
//
//        assertThrows(ExpiredJwtException.class, () -> {
//            jwtUtils.validateJwtToken(expiredJwtToken);
//        });
//    }
//
//    @Test
//    void testValidateJwtToken_InvalidToken() {
//        String invalidJwtToken = "invalidJwtToken";
//
//        assertThrows(SignatureException.class, () -> {
//            jwtUtils.validateJwtToken(invalidJwtToken);
//        });
//    }
}
