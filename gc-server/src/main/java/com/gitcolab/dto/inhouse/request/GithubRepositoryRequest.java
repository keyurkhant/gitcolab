package com.gitcolab.dto.inhouse.request;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GithubRepositoryRequest {
    private String githubAccessToken;
    private String repositoryName;
    private String description;
    private String homepage = "https://github.com";
    private boolean isPrivate = false;
    private boolean isTemplate = true;
}
