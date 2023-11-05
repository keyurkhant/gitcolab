package com.gitcolab.services;

import com.gitcolab.dto.toolExchanges.request.CreateIssueRequest;
import com.gitcolab.dto.toolExchanges.request.GetAccessTokenRequest;
import com.gitcolab.dto.toolExchanges.IssueType;
import com.gitcolab.dto.toolExchanges.request.ProjectCreateRequest;
import com.gitcolab.dto.toolExchanges.response.AccessibleResource;
import com.gitcolab.dto.toolExchanges.response.GetAccessTokenResponse;
import com.gitcolab.dto.toolExchanges.response.MySelfResponse;
import com.gitcolab.dto.toolExchanges.response.ProjectResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;
import reactor.core.publisher.Mono;

import java.util.List;

public interface AtlassianServiceClient {

    @PostExchange("/oauth/token")
    ResponseEntity<GetAccessTokenResponse> getAccessToken(@RequestBody GetAccessTokenRequest getAccessTokenRequest);

//    https://api.atlassian.com
    @GetExchange("/oauth/token/accessible-resources")
    List<AccessibleResource> getAccessibleResources(@RequestHeader("Authorization") String bearerToken);

    @GetExchange("/ex/jira/{cloudId}/rest/api/3/project")
    List<ProjectResponse> getAllProjects(@PathVariable String cloudId, @RequestHeader("Authorization") String bearerToken);
    @GetExchange("/ex/jira/{cloudId}/rest/api/3/project?projectId={projectId}")
    ProjectResponse getProject(@PathVariable String cloudId,@PathVariable String projectId,@RequestHeader("Authorization") String bearerToken);

    @PostExchange("/ex/jira/{cloudId}/rest/api/3/project")
    Mono<ProjectResponse> createProject(@PathVariable String cloudId, @RequestHeader("Authorization") String bearerToken, @RequestBody ProjectCreateRequest createRequest);

    @GetExchange("/ex/jira/{cloudId}/rest/api/3/myself")
    MySelfResponse getUserDetails(@PathVariable String cloudId, @RequestHeader("Authorization") String bearerToken);

    @PostExchange("/ex/jira/{cloudId}/rest/api/3/issue")
    ResponseEntity<?> createIssueRequest(@PathVariable String cloudId, @RequestHeader("Authorization") String bearerToken, @RequestBody CreateIssueRequest createIssueRequest);

    @GetExchange("/ex/jira/{cloudId}/rest/api/3/issuetype/project?projectId={projectId}")
    List<IssueType> getIssues(@PathVariable String cloudId, @PathVariable String projectId, @RequestHeader("Authorization") String bearerToken);


}
