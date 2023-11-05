package com.gitcolab.controllers;

import com.gitcolab.dto.toolExchanges.request.GetAccessTokenRequest;
import com.gitcolab.dto.inhouse.request.GithubAuthRequest;
import com.gitcolab.dto.inhouse.request.GithubRepositoryRequest;
import com.gitcolab.services.AtlassianService;
import com.gitcolab.services.GithubService;
import com.gitcolab.services.UserDetailsImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/integration-tool")
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
public class IntegrationToolController {

    @Autowired
    GithubService githubService;

    @Autowired
    AtlassianService atlassianService;

    @PostMapping("/github/getAccessToken")
    public ResponseEntity<?> getGithubAccessToken(@Valid @RequestBody GithubAuthRequest githubAuthRequest) {
        return githubService.getAccessToken(githubAuthRequest);
    }

    @PostMapping("/atlassian/getAccessToken")
    public ResponseEntity<?> getAtlassianAccessToken(@Valid @RequestBody GetAccessTokenRequest getAccessTokenRequest, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ResponseEntity<?> response=atlassianService.getAccessToken(getAccessTokenRequest,userDetails);
        return response;
    }

    @PostMapping("/generateRepo")
    public ResponseEntity<?> generateRepository(@Valid @RequestBody GithubRepositoryRequest githubRepositoryRequest) {
        return githubService.generateRepository(githubRepositoryRequest);
    }
}
