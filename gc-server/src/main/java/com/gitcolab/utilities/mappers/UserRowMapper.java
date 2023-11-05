package com.gitcolab.utilities.mappers;

import com.gitcolab.entity.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = User.builder()
                .id(rs.getInt("id"))
                .email(rs.getString("email"))
                .username(rs.getString("username"))
                .password(rs.getString("password"))
                .firstName(rs.getString("firstName"))
                .lastName(rs.getString("lastName"))
                .otp(rs.getString("otp"))
                .otpExpiry(rs.getString("otpExpiry"))
                //new things added to row mapper
                .organization(rs.getString("organization"))
                .location(rs.getString("location"))
                .description(rs.getString("description"))
                .linkedin(rs.getString("linkedin"))
                .github(rs.getString("github"))
                .resume(rs.getString("resume"))
                .profilePicture(rs.getString("profilePicture"))
                .build();
        return user;
    }
}
