package com.gitcolab.services;
import com.gitcolab.dto.inhouse.request.GithubAuthRequest;
import com.gitcolab.dto.inhouse.request.GithubRepositoryRequest;
import com.gitcolab.dto.inhouse.response.MessageResponse;
import com.gitcolab.repositories.ToolTokenManagerRepository;
import com.gitcolab.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kohsuke.github.GHCreateRepositoryBuilder;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class GithubServiceTest {
    @Mock
    UserRepository userRepository;

    @Mock
    ToolTokenManagerRepository integrationRepository;

    @Mock
    private WebClient.Builder webClientBuilder;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @Mock
    private GitHubBuilder gitHubBuilder;

    @Mock
    private GitHub github;

    @Mock
    private GHCreateRepositoryBuilder repoBuilder;

    @Mock
    private GHRepository ghRepository;

    private GithubService githubService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        githubService = new GithubService(integrationRepository, userRepository);
    }

    @Test
    void testGetAccessToken_InvalidAuthCode() {
        GithubAuthRequest request = new GithubAuthRequest();
        request.setCode(null);

        ResponseEntity<?> response = githubService.getAccessToken(request);
        MessageResponse expectedResponse = new MessageResponse("Github auth code is invalid.");

        Assertions.assertEquals(400, response.getStatusCodeValue());
        Assertions.assertEquals(expectedResponse, response.getBody());
    }

    @Test
    void testGetAccessToken_InvalidAuthCodeWithEmail() {
        GithubAuthRequest request = new GithubAuthRequest();
        request.setCode(null);
        request.setEmail("example@example.com");

        ResponseEntity<?> response = githubService.getAccessToken(request);
        MessageResponse expectedResponse = new MessageResponse("Github auth code is invalid.");
        Assertions.assertEquals(400, response.getStatusCodeValue());
        Assertions.assertEquals(expectedResponse, response.getBody());
    }

    @Test
    void testGetAccessToken_InvalidEmail() {
        GithubAuthRequest request = new GithubAuthRequest();
        request.setCode("valid_code");
        request.setEmail(null);

        ResponseEntity<?> response = githubService.getAccessToken(request);
        MessageResponse expectedResponse = new MessageResponse("Github auth code is invalid.");

        Assertions.assertEquals(400, response.getStatusCodeValue());
        Assertions.assertEquals(expectedResponse, response.getBody());
    }

    @Test
    public void testGenerateRepository_Success() throws IOException {
        // Mock the GitHub API calls
        String repositoryName = "test-repo";
        String description = "test-repo-desc";
        String accessToken = "your-github-access-token";
        String expectedMessage = "Github facing issue.";
        String homepage = "https://github.com";

        GithubRepositoryRequest githubRepositoryRequest = new GithubRepositoryRequest(accessToken, repositoryName, description, homepage, true, true);

        when(gitHubBuilder.withOAuthToken(accessToken)).thenReturn(gitHubBuilder);
        when(gitHubBuilder.build()).thenReturn(github);
        when(github.createRepository(repositoryName)).thenReturn(repoBuilder);
        when(repoBuilder.create()).thenReturn(ghRepository);

        // Call the method and assert the response
        ResponseEntity<?> response = githubService.generateRepository(githubRepositoryRequest);
        assertEquals(400, response.getStatusCodeValue());
        assertEquals(expectedMessage, ((MessageResponse) response.getBody()).getMessage());
    }
}
