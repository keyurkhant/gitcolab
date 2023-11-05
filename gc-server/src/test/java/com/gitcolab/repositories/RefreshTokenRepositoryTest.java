package com.gitcolab.repositories;

import com.gitcolab.entity.RefreshToken;
import com.gitcolab.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

public class RefreshTokenRepositoryTest {
    private RefreshTokenRepository refreshTokenRepository;

    @BeforeEach
    public void setup() {
        refreshTokenRepository = Mockito.mock(RefreshTokenRepository.class);
    }

    @Test
    public void testSave() {
        // Create a mock RefreshToken
        RefreshToken refreshToken = Mockito.mock(RefreshToken.class);

        // Set up the behavior of the repository's save method
        Mockito.when(refreshTokenRepository.save(refreshToken)).thenReturn(1);

        // Call the save method and assert the result
        int savedRows = refreshTokenRepository.save(refreshToken);
        Assertions.assertEquals(1, savedRows);

        // Verify interactions with the repository
        Mockito.verify(refreshTokenRepository, Mockito.times(1)).save(refreshToken);
    }

    @Test
    public void testFindByToken() {
        // Create a mock RefreshToken
        RefreshToken refreshToken = Mockito.mock(RefreshToken.class);

        // Set up the behavior of the repository's findByToken method
        Mockito.when(refreshTokenRepository.findByToken(Mockito.anyString())).thenReturn(Optional.of(refreshToken));

        // Call the findByToken method and assert the result
        Optional<RefreshToken> foundToken = refreshTokenRepository.findByToken("token");
        Assertions.assertTrue(foundToken.isPresent());
        Assertions.assertEquals(refreshToken, foundToken.get());

        // Verify interactions with the repository
        Mockito.verify(refreshTokenRepository, Mockito.times(1)).findByToken("token");
    }

    @Test
    public void testDeleteByUser() {
        // Create a mock User
        User user = Mockito.mock(User.class);

        // Call the deleteByUser method
        refreshTokenRepository.deleteByUser(user);

        // Verify interactions with the repository
        Mockito.verify(refreshTokenRepository, Mockito.times(1)).deleteByUser(user);
    }

    @Test
    public void testDelete() {
        // Create a mock RefreshToken
        RefreshToken refreshToken = Mockito.mock(RefreshToken.class);

        // Call the delete method
        refreshTokenRepository.delete(refreshToken);

        // Verify interactions with the repository
        Mockito.verify(refreshTokenRepository, Mockito.times(1)).delete(refreshToken);
    }
}
