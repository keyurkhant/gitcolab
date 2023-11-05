package com.gitcolab.dto.toolExchanges.request;

import com.gitcolab.dto.toolExchanges.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashMap;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CreateIssueRequest {
        private Fields fields;

        public CreateIssueRequest(GithubIssueEventRequest githubIssueEventRequest, List<IssueType> issueTypeList, String projectId, String reporterId) {
                HashMap<String,String> issueMap=new HashMap<>();
                issueTypeList.forEach(issueType -> {
                        issueMap.put(issueType.getName(),issueType.getId());
                });
                String title= githubIssueEventRequest.getIssue().getTitle();
                String issueTypeId=title.contains("Bug") || title.contains("bug") ? issueMap.get("Bug"): issueMap.get("Task");
                StringBuilder body=new StringBuilder();
                body.append("Issue Description:\n");

                String descriptionOfIssue= githubIssueEventRequest.getIssue().getBody() != null && !githubIssueEventRequest.getIssue().getBody().equals("null") ? githubIssueEventRequest.getIssue().getBody() : githubIssueEventRequest.getIssue().getTitle();
                body.append(descriptionOfIssue);

                body.append("\n\nReference:\n");

                body.append(githubIssueEventRequest.getIssue().getUrl());

                body.append("\n\nGithub Reporter:\n");
                body.append(githubIssueEventRequest.getIssue().getUser().getLogin());

                ContentItem[] contentItems = {
                        new ContentItem(body.toString() , "text")
                };
                Content[] contents={
                        new Content(contentItems,"paragraph")
                };

                Description description = new Description(contents, "doc", 1);
                IssueType issueType = new IssueType(issueTypeId);
                Reporter reporter = new Reporter(reporterId);
                Project project = new Project(projectId);

                StringBuilder summary = new StringBuilder();
                summary.append(title);
                summary.append(" by ");
                summary.append(githubIssueEventRequest.getIssue().getUser().getLogin());

                this.fields = new Fields(description, issueType, project, reporter, summary.toString());
        }
}
