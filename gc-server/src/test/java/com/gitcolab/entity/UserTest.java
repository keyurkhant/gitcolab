package com.gitcolab.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

public class UserTest {

    private User user;


    @BeforeEach
    public void setUp() {
        user = User.builder()
                .id(1L)
                .username("FNameLName")
                .email("FName.LName@dal.ca")
                .password("password123")
                .firstName("FName")
                .lastName("LName")
                //updating builder (uchenna)
                .location("halifax")
                .organization("dalhousie")
                .description("fakeDescription")
                .linkedin("linkedInLink")
                .github("githubLink")
                .resume("resumeLink")
                .profilePicture("profilePicture")
                .roles(new HashSet<>())
                .build();
    }

    @Test
    public void testGetters() {
        Assertions.assertEquals(1L, user.getId());
        Assertions.assertEquals("FNameLName", user.getUsername());
        Assertions.assertEquals("FName.LName@dal.ca", user.getEmail());
        Assertions.assertEquals("password123", user.getPassword());
        Assertions.assertEquals("FName", user.getFirstName());
        Assertions.assertEquals("LName", user.getLastName());
        Assertions.assertNotNull(user.getRoles());

        //new getter assertions - uchenna
        Assertions.assertEquals("halifax", user.getLocation());
        Assertions.assertEquals("dalhousie", user.getOrganization());
        Assertions.assertEquals("fakeDescription", user.getDescription());
        Assertions.assertEquals("linkedInLink", user.getLinkedin());
        Assertions.assertEquals("githubLink", user.getGithub());
        Assertions.assertEquals("resumeLink", user.getResume());
        Assertions.assertEquals("profilePicture", user.getProfilePicture());
    }

    @Test
    public void testConstructor() {
        User newUser = new User("FNameLName", "FName.LName@example.com", "password456", "FName", "LName");

        //created new testUser
        User newUserUche = new User("Uchenna",  "Dalhousie",  "Halifax", "fakeDescription", "linkedInLink", "githubLink", "resumeLink", "profilePicture");

        Assertions.assertEquals("FNameLName", newUser.getUsername());
        Assertions.assertEquals("FName.LName@example.com", newUser.getEmail());
        Assertions.assertEquals("password456", newUser.getPassword());
        Assertions.assertEquals("FName", newUser.getFirstName());
        Assertions.assertEquals("LName", newUser.getLastName());
        Assertions.assertNotNull(newUser.getRoles());

        //new constructor assertions - uchenna
        Assertions.assertEquals("Uchenna", newUserUche.getUsername());
        Assertions.assertEquals("Dalhousie", newUserUche.getOrganization());
        Assertions.assertEquals("Halifax", newUserUche.getLocation());
        Assertions.assertEquals("fakeDescription", newUserUche.getDescription());
        Assertions.assertEquals("linkedInLink", newUserUche.getLinkedin());
        Assertions.assertEquals("githubLink", newUserUche.getGithub());
        Assertions.assertEquals("resumeLink", newUserUche.getResume());
    }

    @Test
    public void testConstructorTwo() {
        User newUser = new User("example@email.com", "B3L22P", "1600992087");

        Assertions.assertEquals("example@email.com", newUser.getEmail());
        Assertions.assertEquals("B3L22P", newUser.getOtp());
        Assertions.assertEquals("1600992087", newUser.getOtpExpiry());
    }

    @Test
    public void testBuilderConstructor() {
        User newUser = User.builder()
                .username("FNameLName")
                .email("FName.LName@example.com")
                .password("password456")
                .firstName("FName")
                .lastName("LName")
                //new fields in builder - uchenna
                .location("halifax")
                .organization("dalhousie")
                .description("fakeDescription")
                .linkedin("linkedInLink")
                .github("githubLink")
                .resume("resumeLink")
                .build();

        Assertions.assertEquals("FNameLName", newUser.getUsername());
        Assertions.assertEquals("FName.LName@example.com", newUser.getEmail());
        Assertions.assertEquals("password456", newUser.getPassword());
        Assertions.assertEquals("FName", newUser.getFirstName());
        Assertions.assertEquals("LName", newUser.getLastName());
        Assertions.assertNull(newUser.getRoles());

        //New assertions - uchenna
        Assertions.assertEquals("dalhousie", newUser.getOrganization());
        Assertions.assertEquals("halifax", newUser.getLocation());
        Assertions.assertEquals("fakeDescription", newUser.getDescription());
        Assertions.assertEquals("linkedInLink", newUser.getLinkedin());
        Assertions.assertEquals("githubLink", newUser.getGithub());
        Assertions.assertEquals("resumeLink", newUser.getResume());
    }

    @Test
    public void testSetters() {
        user.setId(2L);
        user.setUsername("FNameLName");
        user.setEmail("FName.LName@example.com");
        user.setPassword("newPassword");
        user.setFirstName("FName");
        user.setLastName("LName");
        Set<Role> roles = new HashSet<>();
        user.setRoles(roles);

        //new setters - uchenna
        user.setOrganization("dalhousie");
        user.setLocation("halifax");
        user.setDescription("fakeDescription");
        user.setLinkedin("linkedInLink");
        user.setGithub("githubLink");
        user.setResume("resumeLink");

        Assertions.assertEquals(2L, user.getId());
        Assertions.assertEquals("FNameLName", user.getUsername());
        Assertions.assertEquals("FName.LName@example.com", user.getEmail());
        Assertions.assertEquals("newPassword", user.getPassword());
        Assertions.assertEquals("FName", user.getFirstName());
        Assertions.assertEquals("LName", user.getLastName());
        Assertions.assertEquals(roles, user.getRoles());

        //new assertions - uchenna
        Assertions.assertEquals("dalhousie", user.getOrganization());
        Assertions.assertEquals("halifax", user.getLocation());
        Assertions.assertEquals("fakeDescription", user.getDescription());
        Assertions.assertEquals("linkedInLink", user.getLinkedin());
        Assertions.assertEquals("githubLink", user.getGithub());
        Assertions.assertEquals("resumeLink", user.getResume());
    }
}
