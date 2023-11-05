package com.gitcolab.utilities.mappers;

import com.gitcolab.entity.RefreshToken;
import com.gitcolab.utilities.mappers.RefreshTokenRowMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;

public class RefreshTokenRowMapperTest {

    @Test
    public void testMapRow() throws SQLException {
        ResultSet resultSet = Mockito.mock(ResultSet.class);
        Mockito.when(resultSet.getLong("id")).thenReturn(1L);
        Mockito.when(resultSet.getLong("userId")).thenReturn(2L);
        Mockito.when(resultSet.getString("token")).thenReturn("refreshToken");
        Mockito.when(resultSet.getTimestamp("expiryDate")).thenReturn(Timestamp.from(Instant.now()));

        RefreshTokenRowMapper rowMapper = new RefreshTokenRowMapper();

        RefreshToken refreshToken = rowMapper.mapRow(resultSet, 1);

        Assertions.assertEquals(1L, refreshToken.getId());
        Assertions.assertEquals("refreshToken", refreshToken.getToken());
        Assertions.assertEquals(2L, refreshToken.getUser().getId());

        Mockito.verify(resultSet, Mockito.times(1)).getLong("id");
        Mockito.verify(resultSet, Mockito.times(1)).getLong("userId");
        Mockito.verify(resultSet, Mockito.times(1)).getString("token");
        Mockito.verify(resultSet, Mockito.times(1)).getTimestamp("expiryDate");
    }
}
