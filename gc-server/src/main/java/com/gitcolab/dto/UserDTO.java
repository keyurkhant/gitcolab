package com.gitcolab.dto;

import com.gitcolab.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String organization;
    private String location;
    private String description;
    private String linkedin;
    private String github;
    private String resume;
    private String profilePicture;

    UserDTO(String username) {
        this.username = username;
    }
    public UserDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.organization = user.getOrganization();
        this.location = user.getLocation();
        this.description = user.getDescription();
        this.linkedin = user.getLinkedin();
        this.github = user.getGithub();
        this.resume = user.getResume();
        this.profilePicture = user.getProfilePicture();
    }
}
