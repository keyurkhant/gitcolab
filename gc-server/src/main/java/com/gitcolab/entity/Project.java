package com.gitcolab.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Project {
    private int id;
    private int userId;
    private String repositoryName;
    private String repositoryOwner;
    private String atlassianProjectId;
    private String jiraBoardName;
    private Instant timestamp;

    public Project(int userId, String repositoryName, String repositoryOwner, String jiraBoardName, Instant timestamp) {
        this.userId = userId;
        this.repositoryName = repositoryName;
        this.repositoryOwner = repositoryOwner;
        this.jiraBoardName = jiraBoardName;
        this.timestamp = timestamp;
    }
}