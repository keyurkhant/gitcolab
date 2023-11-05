package com.gitcolab.services;

import com.gitcolab.dto.inhouse.response.MessageResponse;
import com.gitcolab.dto.toolExchanges.request.ProjectCreationRequest;
import com.gitcolab.entity.Project;
import com.gitcolab.repositories.ProjectRepository;
import com.gitcolab.repositories.ToolTokenManagerRepository;
import com.gitcolab.utilities.EmailSender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kohsuke.github.GHMyself;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ProjectServiceTest {

    @Mock
    private ToolTokenManagerRepository toolTokenManagerRepository;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private GithubService githubService;

    @Mock
    private UserService userService;

    @Mock
    private EmailSender emailSender;

    @Mock
    private AtlassianService atlassianService;

    @InjectMocks
    private ProjectService projectService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateProject_RepositoryExists() {
        String repositoryName = "test-repo";
        String githubToken = "test-token";
        ProjectCreationRequest request = new ProjectCreationRequest();
        request.setRepositoryName(repositoryName);
        request.setGithubToken(githubToken);
        UserDetailsImpl userDetails = new UserDetailsImpl(1L, "test-user", "test-email", "test-password", Collections.emptyList());

        GHRepository ghRepository = mock(GHRepository.class);
        when(githubService.getRepositoryByName(repositoryName, githubToken)).thenReturn(ghRepository);

        ResponseEntity<?> response = projectService.createProject(request, userDetails);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Repository already exists.", ((MessageResponse) response.getBody()).getMessage());
    }

    @Test
    void testGetAllProjects() {
        UserDetailsImpl userDetails = new UserDetailsImpl(1L, "test-user", "test-email", "test-password", Collections.emptyList());
        List<Map<String, Object>> projects = new ArrayList<>();
        when(projectRepository.getAllProject(userDetails.getId())).thenReturn(projects);
    
        ResponseEntity<?> response = projectService.getAllProjects(userDetails);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(projects, response.getBody());
    }

    @Test
    void testGetProjectById() {
        int projectId = 1;
        Project project = new Project();
        project.setRepositoryName("test-repo");
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));

        Optional<Project> response = projectService.getProjectById(projectId);
        assertTrue(response.isPresent());
        assertEquals("test-repo", response.get().getRepositoryName());
    }

    @Test
    void testGetDashboardData() {
        long userId = 1L;
        Map<String, Object> dashboardData = new HashMap<>();
        dashboardData.put("totalRepositories", 0);
        dashboardData.put("totalProjectContributions", 0);
        dashboardData.put("numberOfFollowers", 0);
        dashboardData.put("topCommittedRepositories", "");
        dashboardData.put("totalProjectOwnership", 0);
        when(projectRepository.getAllProject(userId)).thenReturn(new ArrayList<>());
        when(projectRepository.getGithubTokenByUserId(userId)).thenReturn("test-token");
    
        ResponseEntity<?> response = projectService.getDashboardData(userId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    

    @Test
    void testGetDashboardAllData() throws IOException {
        long userId = 1L;
        String githubAuthToken = "test-token";
        GitHub gitHub = mock(GitHub.class);
        GHMyself ghMyself = mock(GHMyself.class);
        when(projectRepository.getGithubTokenByUserId(userId)).thenReturn(githubAuthToken);
        when(githubService.getGithubUserByToken(githubAuthToken)).thenReturn(gitHub);
        when(gitHub.getMyself()).thenReturn(ghMyself);
        when(ghMyself.getFollowersCount()).thenReturn(10);
        when(ghMyself.getPublicRepoCount()).thenReturn(5);
        when(githubService.topCommittedRepositories(ghMyself, 10)).thenReturn(Collections.emptyList());

        ResponseEntity<?> response = projectService.getDashboardData(userId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(10, ((Map<?, ?>) response.getBody()).get("numberOfFollowers"));
        assertEquals(5, ((Map<?, ?>) response.getBody()).get("totalRepositories"));
        assertEquals(Collections.emptyList(), ((Map<?, ?>) response.getBody()).get("topCommittedRepositories"));
    }



    @Test
    void testCreateProject_RepositoryDoesNotExist() throws IOException {
        String repositoryName = "test-repo";
        String githubToken = "test-token";
        ProjectCreationRequest request = new ProjectCreationRequest();
        request.setRepositoryName(repositoryName);
        request.setGithubToken(githubToken);
        UserDetailsImpl userDetails = new UserDetailsImpl(1L, "test-user", "test-email", "test-password", Collections.emptyList());

        GHRepository ghRepository = null;
        when(githubService.getRepositoryByName(repositoryName, githubToken)).thenReturn(ghRepository);

        ResponseEntity<?> response = projectService.createProject(request, userDetails);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Something went wrong", ((MessageResponse) response.getBody()).getMessage());
    }

    @Test
    void testGetContributors() {
        int projectId = 1;
        List<Map<String, Object>> contributors = new ArrayList<>();
        when(projectRepository.getAllContributors(projectId)).thenReturn(contributors);
    
        ResponseEntity<?> response = projectService.getContributors(projectId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(contributors, response.getBody());
    }

}
