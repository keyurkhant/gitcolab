package com.gitcolab.services;

import com.gitcolab.configurations.ClientConfig;
import com.gitcolab.dto.UserDTO;
import com.gitcolab.dto.inhouse.request.SendProjectRequest;
import com.gitcolab.dto.toolExchanges.request.GetAccessTokenRequest;
import com.gitcolab.dto.toolExchanges.response.AccessibleResource;
import com.gitcolab.dto.toolExchanges.response.GetAccessTokenResponse;
import com.gitcolab.dto.toolExchanges.response.MySelfResponse;
import com.gitcolab.entity.ToolTokenManager;
import com.gitcolab.entity.User;
import com.gitcolab.repositories.ProjectRepository;
import com.gitcolab.repositories.ToolTokenManagerRepository;
import com.gitcolab.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@SpringBootTest
public class AtlassianServiceTest {

    @Mock
    private ToolTokenManagerRepository toolTokenManagerRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ClientConfig clientConfig;

    @Mock
    private AtlassianServiceClient atlassianServiceClient;

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private AtlassianService atlassianService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAccessToken() {
        GetAccessTokenRequest getAccessTokenRequest = new GetAccessTokenRequest();
        getAccessTokenRequest.setCode("code");
        UserDetailsImpl userDetails = new UserDetailsImpl(1L, "user", "user@example.com", "password", null);
        GetAccessTokenResponse getAccessTokenResponse = new GetAccessTokenResponse();
        getAccessTokenResponse.setAccess_token("access_token");
        when(atlassianServiceClient.getAccessToken(getAccessTokenRequest)).thenReturn(ResponseEntity.ok(getAccessTokenResponse));
        ResponseEntity<?> response = atlassianService.getAccessToken(getAccessTokenRequest, userDetails);
        assertEquals(400, response.getStatusCodeValue());
        verify(toolTokenManagerRepository, times(1)).save(any(ToolTokenManager.class));
    }

    @Test
    void testGetAccessibleResources() {
        AccessibleResource accessibleResource = new AccessibleResource();
        accessibleResource.setId("id");
        when(atlassianServiceClient.getAccessibleResources("Bearer token")).thenReturn(Arrays.asList(accessibleResource));
        Optional<AccessibleResource> result = atlassianService.getAccessibleResources("Bearer token");
        assertTrue(result.isPresent());
        assertEquals("id", result.get().getId());
    }

    @Test
    void testGetUserDetails() {
        MySelfResponse mySelfResponse = new MySelfResponse();
        mySelfResponse.setAccountId("accountId");
        when(atlassianServiceClient.getUserDetails("cloudId", "Bearer token")).thenReturn(mySelfResponse);
        Optional<MySelfResponse> result = atlassianService.getUserDetails("Bearer token", "cloudId");
        assertTrue(result.isPresent());
        assertEquals("accountId", result.get().getAccountId());
    }

    // @Test
    // void testCreateAtlassianProject() {
    //     ProjectCreationRequest projectCreationRequest = new ProjectCreationRequest();
    //     projectCreationRequest.setAtlassianToken("atlassianToken");
    //     projectCreationRequest.setRepositoryName("repositoryName");
    //     AccessibleResource accessibleResource = new AccessibleResource();
    //     accessibleResource.setId("cloudId");
    //     when(atlassianServiceClient.getAccessibleResources("Bearer atlassianToken")).thenReturn(Arrays.asList(accessibleResource));
    //     MySelfResponse mySelfResponse = new MySelfResponse();
    //     mySelfResponse.setAccountId("accountId");
    //     when(atlassianServiceClient.getUserDetails("Bearer atlassianToken", "cloudId")).thenReturn(mySelfResponse);
    //     ResponseEntity<?> response = atlassianService.createAtlassianProject(projectCreationRequest);
    //     assertEquals(200, response.getStatusCodeValue());
    // }

    // @Test
    // void testCreateIssue() {
    //     GithubIssueEvent githubIssueEvent = new GithubIssueEvent();
    //     githubIssueEvent.setRepositoryName("repositoryName");
    //     String projectId = "projectId";
    //     String bearerToken = "Bearer token";
    //     AccessibleResource accessibleResource = new AccessibleResource();
    //     accessibleResource.setId("cloudId");
    //     when(atlassianServiceClient.getAccessibleResources(bearerToken)).thenReturn(Arrays.asList(accessibleResource));
    //     MySelfResponse mySelfResponse = new MySelfResponse();
    //     mySelfResponse.setAccountId("accountId");
    //     when(atlassianServiceClient.getUserDetails(bearerToken, "cloudId")).thenReturn(mySelfResponse);
    //     ResponseEntity<?> response = atlassianService.createIssue(githubIssueEvent, projectId, bearerToken);
    //     assertEquals(200, response.getStatusCodeValue());
    // }

    // @Test
    // void testGenerateKey() {
    //     String name = "name";
    //     String key = AtlassianService.generateKey(name);
    //     assertTrue(key.startsWith("NAME"));
    //     assertEquals(9, key.length());
    // }
}
