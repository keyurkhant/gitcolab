package com.gitcolab.utilities.mappers;

import com.gitcolab.entity.RefreshToken;
import com.gitcolab.entity.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;

public class RefreshTokenRowMapper implements RowMapper<RefreshToken> {

    @Override
    public RefreshToken mapRow(ResultSet rs, int rowNum) throws SQLException {
        long id = rs.getLong("id");
        long userId = rs.getLong("userId");
        String token = rs.getString("token");
        Instant expiryDate = rs.getTimestamp("expiryDate").toInstant();

        User user = new User();
        user.setId(userId);

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setId(id);
        refreshToken.setUser(user);
        refreshToken.setToken(token);
        refreshToken.setExpiryDate(expiryDate);

        return refreshToken;
    }
}
