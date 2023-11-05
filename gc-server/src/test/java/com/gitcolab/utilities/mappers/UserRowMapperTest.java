package com.gitcolab.utilities.mappers;

import com.gitcolab.entity.User;
import com.gitcolab.utilities.mappers.UserRowMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@SpringBootTest
public class UserRowMapperTest {


    @Test
    public void testMapRow() throws SQLException {
        // Mock the ResultSet and set up necessary values
        ResultSet resultSet = Mockito.mock(ResultSet.class);
        Mockito.when(resultSet.getInt("id")).thenReturn(1);
        Mockito.when(resultSet.getString("email")).thenReturn("test@example.com");
        Mockito.when(resultSet.getString("username")).thenReturn("testuser");
        Mockito.when(resultSet.getString("password")).thenReturn("password");
        Mockito.when(resultSet.getString("firstName")).thenReturn("John");
        Mockito.when(resultSet.getString("lastName")).thenReturn("Doe");


        //New ResultSet mocks (Uchenna)
        Mockito.when(resultSet.getString("organization")).thenReturn("dalhousie");
        Mockito.when(resultSet.getString("location")).thenReturn("halifax");
        Mockito.when(resultSet.getString("description")).thenReturn("userDescription");
        Mockito.when(resultSet.getString("linkedin")).thenReturn("linkedInLink");
        Mockito.when(resultSet.getString("github")).thenReturn("githubLink");
        Mockito.when(resultSet.getString("resume")).thenReturn("resumeLink");


        // Create an instance of UserRowMapper
        RowMapper<User> userRowMapper = new UserRowMapper();

        // Call the mapRow method and assert the result
        User user = userRowMapper.mapRow(resultSet, 1);
        Assertions.assertNotNull(user);
        Assertions.assertEquals(1, user.getId());
        Assertions.assertEquals("test@example.com", user.getEmail());
        Assertions.assertEquals("testuser", user.getUsername());
        Assertions.assertEquals("password", user.getPassword());
        Assertions.assertEquals("John", user.getFirstName());
        Assertions.assertEquals("Doe", user.getLastName());

        //New Assertion Tests (Uchenna) -> we're asserting hard coded values against the hard coded resultset (mock db query representation)
        Assertions.assertEquals("dalhousie", user.getOrganization());
        Assertions.assertEquals("userDescription", user.getDescription());
        Assertions.assertEquals("linkedInLink", user.getLinkedin());
        Assertions.assertEquals("githubLink", user.getGithub());
        Assertions.assertEquals("resumeLink", user.getResume());
        Assertions.assertEquals("halifax", user.getLocation());


        // Verify interactions with the ResultSet
        Mockito.verify(resultSet, Mockito.times(1)).getInt("id");
        Mockito.verify(resultSet, Mockito.times(1)).getString("email");
        Mockito.verify(resultSet, Mockito.times(1)).getString("username");
        Mockito.verify(resultSet, Mockito.times(1)).getString("password");
        Mockito.verify(resultSet, Mockito.times(1)).getString("firstName");
        Mockito.verify(resultSet, Mockito.times(1)).getString("lastName");

        //New Verification Tests (Uchenna) -> we're verifying that result set is behaving how it should be
        Mockito.verify(resultSet, Mockito.times(1)).getString("organization");
        Mockito.verify(resultSet, Mockito.times(1)).getString("location");
        Mockito.verify(resultSet, Mockito.times(1)).getString("description");
        Mockito.verify(resultSet, Mockito.times(1)).getString("linkedin");
        Mockito.verify(resultSet, Mockito.times(1)).getString("github");
        Mockito.verify(resultSet, Mockito.times(1)).getString("resume");

    }
}
