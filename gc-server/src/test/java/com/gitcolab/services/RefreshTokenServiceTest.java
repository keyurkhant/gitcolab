package com.gitcolab.services;

import com.gitcolab.entity.RefreshToken;
import com.gitcolab.entity.User;
import com.gitcolab.exceptions.TokenRefreshException;
import com.gitcolab.repositories.RefreshTokenRepository;
import com.gitcolab.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.lang.reflect.Field;
import java.time.Instant;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestPropertySource(properties = "gitcolab.app.jwtRefreshExpirationMs=10000")
class RefreshTokenServiceTest {

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private RefreshTokenService refreshTokenService;

    @BeforeEach
    void setUp() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        MockitoAnnotations.openMocks(this);
        // refreshTokenService.refreshTokenDurationMs = 60000L; // Set a test value for refreshTokenDurationMs

        // Use reflection to set a value for refreshTokenDurationMs
        Field refreshTokenDurationMsField = RefreshTokenService.class.getDeclaredField("refreshTokenDurationMs");
        refreshTokenDurationMsField.setAccessible(true);
        refreshTokenDurationMsField.set(refreshTokenService, 60000L); // Set a test value for refreshTokenDurationMs
    }

    @Test
    void testFindByToken() {
        String token = "sampleToken";
        RefreshToken refreshToken = new RefreshToken();
        when(refreshTokenRepository.findByToken(token)).thenReturn(Optional.of(refreshToken));

        Optional<RefreshToken> result = refreshTokenService.findByToken(token);

        assertTrue(result.isPresent());
        assertEquals(refreshToken, result.get());
        verify(refreshTokenRepository).findByToken(token);
    }

    @Test
    void testVerifyExpiration() {
        RefreshToken validToken = createValidRefreshToken();
        RefreshToken expiredToken = createExpiredRefreshToken();

        RefreshToken resultValid = refreshTokenService.verifyExpiration(validToken);
        assertThrows(TokenRefreshException.class, () -> refreshTokenService.verifyExpiration(expiredToken));

        assertNotNull(resultValid);
        assertEquals(validToken, resultValid);
        assertThrows(TokenRefreshException.class, () -> refreshTokenService.verifyExpiration(expiredToken));
    }

    @Test
    void testCreateRefreshToken() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userId);

        assertNotNull(refreshToken);
        assertEquals(user, refreshToken.getUser());
        verify(userRepository).findById(userId);
        verify(refreshTokenRepository).save(refreshToken);
    }

    @Test
    void testCreateRefreshToken_UserNotFound() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> refreshTokenService.createRefreshToken(userId));
        verify(userRepository).findById(userId);
        verify(refreshTokenRepository, never()).save(any(RefreshToken.class));
    }

    private RefreshToken createValidRefreshToken() {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setExpiryDate(Instant.now().plusMillis(60000L));
        return refreshToken;
    }

    private RefreshToken createExpiredRefreshToken() {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setExpiryDate(Instant.now().minusMillis(60000L));
        return refreshToken;
    }
}
