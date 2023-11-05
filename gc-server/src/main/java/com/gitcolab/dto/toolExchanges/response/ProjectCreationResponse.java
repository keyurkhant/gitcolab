package com.gitcolab.dto.toolExchanges.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectCreationResponse {
    private String repositoryName;
    private String jiraBoardName;
}
