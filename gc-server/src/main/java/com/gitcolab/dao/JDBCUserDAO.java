package com.gitcolab.dao;

import com.gitcolab.entity.User;
import com.gitcolab.utilities.mappers.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JDBCUserDAO implements UserDAO{
    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Optional get(long id) {
        User user=jdbcTemplate.queryForObject("select * from User u where u.id=?",new Object[]{id}, new UserRowMapper());
        return Optional.of(user);
    }

    @Override
    public int save(Object o) {
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(o);
        return namedParameterJdbcTemplate
                .update("INSERT INTO User(`username`,`email`,`password`,`firstName`,`lastName`) values(:username,:email,:password,:firstName,:lastName)"
                        ,namedParameters);
    }

    @Override
    public int update(Object o) {
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(o);
        return namedParameterJdbcTemplate
                .update("UPDATE User SET `username` = :username,`email` = :email,`password` = :password,`firstName` = :firstName,`lastName` = :lastName, `otp` = :otp, `otpExpiry` = :otpExpiry WHERE `email` = :email"
                        ,namedParameters);
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public Optional<User> getUserByUserName(String username) {
        User user = jdbcTemplate.queryForObject("select * from User u where u.username=?",new Object[]{username}, new UserRowMapper());
        return Optional.of(user);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        User user=jdbcTemplate.queryForObject("select * from User u where u.email=?",new Object[]{email}, new UserRowMapper());
        return Optional.of(user);
    }

    @Override
    public boolean existsByUsername(String username) {
        return jdbcTemplate.queryForObject("select count(*) from User u where u.username=?",new Object[]{username},Integer.class)>0;
    }

    @Override
    public boolean existsByEmail(String email) {
        return jdbcTemplate.queryForObject("select count(*) from User u where u.email=?",new Object[]{email},Integer.class)>0;
    }

    //new method to eliminate confusion
    @Override
    public int updateProfile(Object o) {
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(o);
        return namedParameterJdbcTemplate
                .update("UPDATE User SET " +
                                "`organization` = :organization, `location` = :location, `description` = :description, `linkedin` = :linkedin, `github` = :github," +
                                "`resume` = :resume, `profilePicture` = :profilePicture" +
                                " WHERE `username` = :username"
                        ,namedParameters);
    }

}
