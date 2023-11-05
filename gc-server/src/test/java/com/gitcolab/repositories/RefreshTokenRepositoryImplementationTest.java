package com.gitcolab.repositories;

import com.gitcolab.dao.RefreshTokenDAO;
import com.gitcolab.entity.RefreshToken;
import com.gitcolab.entity.User;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class RefreshTokenRepositoryImplementationTest {

    @Mock
    private RefreshTokenDAO refreshTokenDAO;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private RefreshTokenRepositoryImplementation refreshTokenRepository;

    @Test
    public void testSave() {
        RefreshToken refreshToken = new RefreshToken();

        when(refreshTokenDAO.save(refreshToken)).thenReturn(1);

        int result = refreshTokenRepository.save(refreshToken);

        assertEquals(1, result);
        verify(refreshTokenDAO, times(1)).save(refreshToken);
    }

    @Test
    public void testFindByToken_WithExistingToken() {
        String token = "sample-token";
        RefreshToken refreshToken = new RefreshToken();
        User user = new User();
        user.setId(1L);
        refreshToken.setUser(user);
        Optional<RefreshToken> optionalRefreshToken = Optional.of(refreshToken);

        when(refreshTokenDAO.findByToken(token)).thenReturn(optionalRefreshToken);

        User completeUserData = new User();
        completeUserData.setId(1L);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(completeUserData));

        Optional<RefreshToken> result = refreshTokenRepository.findByToken(token);

        assertTrue(result.isPresent());
        assertEquals(completeUserData.getId(), result.get().getUser().getId());
        verify(refreshTokenDAO, times(1)).findByToken(token);
        verify(userRepository, times(1)).findById(user.getId());
    }

    @Test
    public void testFindByToken_WithNonExistingToken() {
        String token = "non-existing-token";

        when(refreshTokenDAO.findByToken(token)).thenReturn(Optional.empty());

        Optional<RefreshToken> result = refreshTokenRepository.findByToken(token);

        assertFalse(result.isPresent());
        verify(refreshTokenDAO, times(1)).findByToken(token);
    }

    @Test
    public void testDeleteByUser() {
        User user = new User();
        user.setId(1L);

        refreshTokenRepository.deleteByUser(user);

        verify(refreshTokenDAO, times(1)).deleteByUser(user);
    }

    @Test
    public void testDelete() {
        RefreshToken refreshToken = new RefreshToken();

        refreshTokenRepository.delete(refreshToken);

        verify(refreshTokenDAO, times(1)).delete(refreshToken);
    }

}