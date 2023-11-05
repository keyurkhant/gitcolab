package com.gitcolab.dto.inhouse.request;

import com.gitcolab.dto.inhouse.request.UpdateUserProfileRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class UpdateUserProfileRequestTest {
    @Test
    void getUsername_ShouldReturnUsername() {
        // Arrange
        String expectedUsername = "Uchenna";
        UpdateUserProfileRequest userProfileRequest = new UpdateUserProfileRequest("Uchenna","Dalhousie",
                                                                            "Halifax","testDesc",
                                                                            "linkedIn","github", "resume", "profilePicture");
        // Act
        String actualUsername = userProfileRequest.getUsername();

        // Assert
        assertEquals(expectedUsername, actualUsername);
    }

    @Test
    void setUsername_ShouldSetUsername() {
        // Arrange
        String newUsername = "Uchenna";

        UpdateUserProfileRequest userProfileRequest = new UpdateUserProfileRequest();

        // Act
        userProfileRequest.setUsername(newUsername);

        // Assert
        assertEquals(newUsername, userProfileRequest.getUsername());
    }

    @Test
    void getOrganization_ShouldReturnOrganization() {
        // Arrange
        String expectedOrganization = "Dalhousie";
        UpdateUserProfileRequest userProfileRequest = new UpdateUserProfileRequest("Uchenna","Dalhousie",
                "Halifax","testDesc","linkedIn","github", "resume", "profilePicture");
        // Act
        String actualOrganization = userProfileRequest.getOrganization();

        // Assert
        assertEquals(expectedOrganization, actualOrganization);
    }

    @Test
    void setOrganization_ShouldSetOrganization() {
        // Arrange
        String organization = "Dalhousie";
        UpdateUserProfileRequest userProfileRequest = new UpdateUserProfileRequest();

        // Act
        userProfileRequest.setOrganization(organization);

        // Assert
        assertEquals(organization, userProfileRequest.getOrganization());
    }

    @Test
    void getLocation_ShouldReturnLocation() {
        // Arrange
        String expectedLocation= "Halifax";
        UpdateUserProfileRequest userProfileRequest = new UpdateUserProfileRequest("Uchenna","Dalhousie",
                "Halifax","testDesc","linkedIn","github", "resume", "profilePicture");
        // Act
        String actualLocation = userProfileRequest.getLocation();

        // Assert
        assertEquals(expectedLocation, actualLocation);
    }

    @Test
    void setLocation_ShouldSetLocation() {
        // Arrange
        String location = "Halifax";
        UpdateUserProfileRequest userProfileRequest = new UpdateUserProfileRequest();

        // Act
        userProfileRequest.setLocation(location);

        // Assert
        assertEquals(location, userProfileRequest.getLocation());
    }

    @Test
    void getDescription_ShouldReturnDescription() {
        // Arrange
        String expectedDescription= "testDesc";
        UpdateUserProfileRequest userProfileRequest = new UpdateUserProfileRequest("Uchenna","Dalhousie",
                "Halifax","testDesc","linkedIn","github", "resume", "profilePicture");
        // Act
        String actualDescription = userProfileRequest.getDescription();

        // Assert
        assertEquals(expectedDescription, actualDescription);
    }

    @Test
    void setDescription_ShouldSetDescription() {
        // Arrange
        String description = "testDesc";
        UpdateUserProfileRequest userProfileRequest = new UpdateUserProfileRequest();

        // Act
        userProfileRequest.setDescription(description);

        // Assert
        assertEquals(description, userProfileRequest.getDescription());
    }

    @Test
    void getLinkedIn_ShouldReturnLinkedIn() {
        // Arrange
        String expectedLinkedIn = "linkedIn";
        UpdateUserProfileRequest userProfileRequest = new UpdateUserProfileRequest("Uchenna","Dalhousie",
                "Halifax","testDesc","linkedIn","github", "resume", "profilePicture");
        // Act
        String actualLinkedIn = userProfileRequest.getLinkedin();

        // Assert
        assertEquals(expectedLinkedIn, actualLinkedIn);
    }

    @Test
    void setLinkedIn_ShouldSetLinkedIn() {
        // Arrange
        String linkedIn = "linkedIn";
        UpdateUserProfileRequest userProfileRequest = new UpdateUserProfileRequest();

        // Act
        userProfileRequest.setLinkedin(linkedIn);

        // Assert
        assertEquals(linkedIn, userProfileRequest.getLinkedin());
    }

    @Test
    void getGitHub_ShouldReturnGitHub() {
        // Arrange
        String expectedGitHub = "github";
        UpdateUserProfileRequest userProfileRequest = new UpdateUserProfileRequest("Uchenna","Dalhousie",
                "Halifax","testDesc","linkedIn","github", "resume", "profilePicture");
        // Act
        String actualGitHub = userProfileRequest.getGithub();

        // Assert
        assertEquals(expectedGitHub, actualGitHub);
    }

    @Test
    void setGitHub_ShouldSetGitHub() {
        // Arrange
        String gitHub = "github";
        UpdateUserProfileRequest userProfileRequest = new UpdateUserProfileRequest();

        // Act
        userProfileRequest.setGithub(gitHub);

        // Assert
        assertEquals(gitHub, userProfileRequest.getGithub());
    }

    @Test
    void getResume_ShouldReturnResume() {
        // Arrange
        String expectedResume = "resume";
        UpdateUserProfileRequest userProfileRequest = new UpdateUserProfileRequest("Uchenna","Dalhousie",
                "Halifax","testDesc","linkedIn","github", "resume", "profilePicture");
        // Act
        String actualResume = userProfileRequest.getResume();

        // Assert
        assertEquals(expectedResume, actualResume);
    }

    @Test
    void setResume_ShouldSetResume() {
        // Arrange
        String resume = "resume";
        UpdateUserProfileRequest userProfileRequest = new UpdateUserProfileRequest();

        // Act
        userProfileRequest.setResume(resume);

        // Assert
        assertEquals(resume, userProfileRequest.getResume());
    }
    @Test
    void getProfilePicture_ShouldReturnProfilePicture() {
        // Arrange
        String expectedProfilePicture = "profilePicture";
        UpdateUserProfileRequest userProfileRequest = new UpdateUserProfileRequest("Uchenna","Dalhousie",
                "Halifax","testDesc","linkedIn","github", "resume", "profilePicture");
        // Act
        String actualProfilePicture = userProfileRequest.getProfilePicture();

        // Assert
        assertEquals(expectedProfilePicture, actualProfilePicture);
    }

    @Test
    void setProfilePicture_ShouldReturnProfilePicture() {
        // Arrange
        String profilePicture = "profilePicture";
        UpdateUserProfileRequest userProfileRequest = new UpdateUserProfileRequest();

        // Act
        userProfileRequest.setProfilePicture(profilePicture);

        // Assert
        assertEquals(profilePicture, userProfileRequest.getProfilePicture());
    }
}