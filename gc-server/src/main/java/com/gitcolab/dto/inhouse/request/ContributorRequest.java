package com.gitcolab.dto.inhouse.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ContributorRequest {
    private String username;
    private String email;
    private String githubAuthToken;
    private String repositoryName;
}
