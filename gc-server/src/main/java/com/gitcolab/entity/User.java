package com.gitcolab.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    private long id;

    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Set<Role> roles = new HashSet<>();
    private String otp;
    private String otpExpiry;

    /*added fields for updating*/
    private  String organization;
    private  String location;
    private  String description;
    private  String linkedin;
    private  String github;
    private  String resume;
    private  String profilePicture;



    public User(String username, String email, String password, String firstName, String lastName) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User(String email, String otp, String otpExpiry) {
        this.email = email;
        this.otp = otp;
        this.otpExpiry = otpExpiry;
    }

    public User(String username, String email, String password, String uFname, String uLname, String abc123, String s) {
    }

    public User(String username, String organization, String location, String description, String linkedin, String github, String resume, String profilePicture) {
        this.username = username;
        this.organization = organization;
        this.location = location;
        this.description = description;
        this.linkedin = linkedin;
        this.github = github;
        this.resume = resume;
    }


}
