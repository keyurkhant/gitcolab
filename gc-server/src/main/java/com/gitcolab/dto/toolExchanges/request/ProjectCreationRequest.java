package com.gitcolab.dto.toolExchanges.request;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectCreationRequest {
    private String email;
    private String githubToken;
    private String repositoryName;
    private String atlassianToken;
    @JsonProperty
    private boolean isAtlassianRequired;
    private String jiraBoardName;
}
