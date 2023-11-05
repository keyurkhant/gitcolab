package com.gitcolab.dto.inhouse.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
@AllArgsConstructor

//This is essentially a UserDTO, Didn't change the base UserDTO class to avoid any potential conflicts with already written code.
public class UpdateUserProfileRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String organization;

    @NotBlank
    private String location;

    @NotBlank
    private String description;

    @NotBlank
    private String linkedin;

    @NotBlank
    private String github;

    //per discussion with Baseer, resume will just be a link to a google drive.
    @NotBlank
    private String resume;

    @NotBlank
    private String profilePicture;
}
