package com.gitcolab.dao;

import com.gitcolab.entity.RefreshToken;
import com.gitcolab.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class RefreshTokenDAOTest {

    private RefreshTokenDAO refreshTokenDAO;
    private RefreshToken refreshToken;

    private User user;

    @BeforeEach
    void setUp(){
        refreshToken=mock(RefreshToken.class);
        refreshTokenDAO=mock(RefreshTokenDAO.class);
        user=mock(User.class);
    }

    @Test
    void testFindByToken(){
        String token="token";
        when(refreshTokenDAO.findByToken(token)).thenReturn(Optional.of(refreshToken));
        Optional<RefreshToken> result=refreshTokenDAO.findByToken(token);
        assertTrue(result.isPresent());
        assertEquals(refreshToken,result.get());
        verify(refreshTokenDAO).findByToken(token);
    }

    @Test
    void testFindByTokenNotFound() {
        String token = "tokenWhichDoesntExist";
        when(refreshTokenDAO.findByToken(token)).thenReturn(Optional.empty());

        Optional<RefreshToken> result = refreshTokenDAO.findByToken(token);

        assertFalse(result.isPresent());

        verify(refreshTokenDAO).findByToken(token);
    }
    @Test
    void testDeleteByUser(){
        refreshTokenDAO.deleteByUser(user);
        verify(refreshTokenDAO).deleteByUser(user);
    }
    @Test
    void delete(){
        refreshTokenDAO.delete(refreshToken);
        verify(refreshTokenDAO).delete(refreshToken);
    }
}