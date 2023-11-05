package com.gitcolab.dao;

import com.gitcolab.entity.RefreshToken;
import com.gitcolab.entity.User;
import com.gitcolab.utilities.mappers.RefreshTokenRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JDBCRefreshTokenDAO implements RefreshTokenDAO {
    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Override
    public Optional get(long id) {
        RefreshToken refreshToken=jdbcTemplate.queryForObject("select * from RefreshToken rt where rt.id=?",new Object[]{id}, new RefreshTokenRowMapper());
        return Optional.of(refreshToken);
    }

    @Override
    public int save(Object o) {
        RefreshToken refreshToken=(RefreshToken) o;
        String sql = "INSERT INTO RefreshToken (id, userId, token, expiryDate) " +
                "VALUES (:id, :userId, :token, :expiryDate)";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", refreshToken.getId());
        parameters.addValue("userId", refreshToken.getUser().getId());
        parameters.addValue("token", refreshToken.getToken());
        parameters.addValue("expiryDate", refreshToken.getExpiryDate());
        return namedParameterJdbcTemplate.update(sql, parameters);
    }

    @Override
    public int update(Object o) {
        return 0;
    }

    @Override
    public void delete(long id) {
        String sql = "DELETE FROM RefreshToken WHERE id = :id";
        jdbcTemplate.update(sql,new Object[]{id});
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        RefreshToken refreshToken=jdbcTemplate.queryForObject("select * from RefreshToken rt where rt.token=?",new Object[]{token}, new RefreshTokenRowMapper());
        return Optional.of(refreshToken);
    }

    @Override
    public void deleteByUser(User user) {
        String sql = "DELETE FROM RefreshToken rt WHERE rt.userId = :id";
        jdbcTemplate.update(sql,new Object[]{user.getId()});
    }

    @Override
    public void delete(RefreshToken t) {
        delete(t.getId());
    }
}
