package com.gitcolab.dto.toolExchanges.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectCreateRequest {
    final private String assigneeType="UNASSIGNED";
    private String description;
    private String key;
    private String leadAccountId;
    private String name;
    final String projectTemplateKey="com.pyxis.greenhopper.jira:gh-simplified-agility-scrum";
    final String projectTypeKey="software";
    final String url="http://atlassian.com";

}