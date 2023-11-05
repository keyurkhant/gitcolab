package com.gitcolab.dto.toolExchanges.request;

import com.gitcolab.dto.toolExchanges.IssueData;
import com.gitcolab.dto.toolExchanges.RepositoryData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GithubIssueEventRequest {
    private String action;
    private IssueData issue;
    private RepositoryData repository;
}

