package com.gitcolab.services;

import com.gitcolab.dto.inhouse.request.SendProjectRequest;
import com.gitcolab.dto.toolExchanges.request.GithubIssueEventRequest;
import com.gitcolab.dto.toolExchanges.request.ProjectCreationRequest;
import com.gitcolab.dto.toolExchanges.response.ProjectCreationResponse;
import com.gitcolab.dto.inhouse.request.ContributorRequest;
import com.gitcolab.dto.inhouse.response.MessageResponse;
import com.gitcolab.dto.inhouse.request.RegisterUserRequest;
import com.gitcolab.dto.UserDTO;
import com.gitcolab.dto.toolExchanges.response.ProjectResponse;
import com.gitcolab.entity.EnumIntegrationType;
import com.gitcolab.entity.Project;
import com.gitcolab.entity.ToolTokenManager;
import com.gitcolab.entity.User;
import com.gitcolab.repositories.ProjectRepository;
import com.gitcolab.repositories.ToolTokenManagerRepository;
import com.gitcolab.utilities.EmailSender;
import com.gitcolab.utilities.HelperUtils;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.Optional;

@Service
public class ProjectService {
    private static final Logger logger = LoggerFactory.getLogger(ProjectService.class);
    ToolTokenManagerRepository toolTokenManagerRepository;
    ProjectRepository projectRepository;

    GithubService githubService;
    UserService userService;

    @Autowired
    EmailSender emailSender;
    AtlassianService atlassianService;

    @Autowired
    public ProjectService(ToolTokenManagerRepository toolTokenManagerRepository, GithubService githubService, ProjectRepository projectRepository, AtlassianService atlassianService, UserService userService) {
        this.toolTokenManagerRepository = toolTokenManagerRepository;
        this.githubService = githubService;
        this.projectRepository = projectRepository;
        this.atlassianService = atlassianService;
        this.userService = userService;
    }

    public ResponseEntity<?> createProject(ProjectCreationRequest projectCreationRequest, UserDetailsImpl userDetails) {
        String repositoryName = projectCreationRequest.getRepositoryName();
        String githubToken = projectCreationRequest.getGithubToken();
//        String atlassianProjectId = "-1";

        if (githubService.getRepositoryByName(repositoryName, githubToken) != null) {
            return ResponseEntity.badRequest().body(new MessageResponse("Repository already exists."));
        }
        if (projectRepository.isJiraBoardExist(projectCreationRequest.getJiraBoardName(), Long.toString(userDetails.getId()))) {
            return ResponseEntity.badRequest().body(new MessageResponse("Jira Board With Given Name Already Exists"));
        }
        if (projectCreationRequest.isAtlassianRequired() && (projectCreationRequest.getAtlassianToken() == null || projectCreationRequest.getAtlassianToken().isEmpty())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Atlassian auth code is invalid."));
        }
        if (githubService.generateRepository(repositoryName, githubToken)) {
            try {
                GitHub gitHub = githubService.getGithubUserByToken(githubToken);
                if (gitHub == null)
                    return ResponseEntity.badRequest().body(new MessageResponse("User not exists."));
                Project project = new Project(
                        Math.toIntExact(userDetails.getId()),
                        repositoryName,
                        gitHub.getMyself().getLogin(),
                        projectCreationRequest.getJiraBoardName(),
                        Instant.now());

                projectRepository.save(project);

                Optional<Project> createdProject = projectRepository.findByRepositoryName(repositoryName);
                if (projectCreationRequest.isAtlassianRequired() && createdProject.isPresent()) {
                    githubService.generateWebHook(repositoryName, githubToken);
                    atlassianService.createAtlassianProject(projectCreationRequest)
                            .doOnError(throwable -> {
                                logger.error("Error while creating Atlassian Project->" + throwable.getMessage());
                            })
                            .subscribe(projectResponse -> {
                                logger.info("Received Response From Atlassian API->", projectResponse);
                                addAtlassianProjectId(projectResponse,createdProject.get());
                            });

                }
                projectRepository.addContributor(Math.toIntExact(userDetails.getId()), createdProject.get().getId());
                return ResponseEntity.ok().body(new ProjectCreationResponse(repositoryName, projectCreationRequest.getJiraBoardName()));
            } catch (Exception e) {
                return ResponseEntity.badRequest().body(new MessageResponse("Something went wrong"));
            }
        }
        return ResponseEntity.badRequest().body(new MessageResponse("Something went wrong"));
    }

    private void addAtlassianProjectId(ProjectResponse projectResponse, Project createdProjectObject) {
        createdProjectObject.setAtlassianProjectId(projectResponse.getId());
        projectRepository.update(createdProjectObject);
    }

    public ResponseEntity<?> getAllProjects(UserDetailsImpl userDetails) {
        return ResponseEntity.ok().body(projectRepository.getAllProject(userDetails.getId()));
    }

    public Optional<Project> getProjectById(int projectId) {
        return projectRepository.findById(projectId);
    }

    public ResponseEntity<List<Map<String, Object>>> getContributors(int projectId) {
        return ResponseEntity.ok().body(projectRepository.getAllContributors(projectId));
    }

    public ResponseEntity<?> addContributor(ContributorRequest contributorRequest) {
        if (!HelperUtils.isValidString(contributorRequest.getUsername()) || !HelperUtils.isValidString(contributorRequest.getEmail()))
            return ResponseEntity.badRequest().body(new MessageResponse("Invalid request"));

        if (!HelperUtils.isValidString(contributorRequest.getGithubAuthToken()) || !HelperUtils.isValidString(contributorRequest.getRepositoryName()))
            return ResponseEntity.badRequest().body(new MessageResponse("Invalid github information"));

        GHRepository ghRepository = githubService.getRepositoryByName(contributorRequest.getRepositoryName(), contributorRequest.getGithubAuthToken());
        if (ghRepository == null) {
            return ResponseEntity.badRequest().body(new MessageResponse("Repository not exists"));
        }
        boolean isContributorAdded = githubService.addCollaborator(
                contributorRequest.getRepositoryName(),
                contributorRequest.getUsername(),
                contributorRequest.getGithubAuthToken()
        );
        if (!isContributorAdded)
            return ResponseEntity.badRequest().body(new MessageResponse("Something went wrong"));

        String password = HelperUtils.generateRandomPassword(8);
        RegisterUserRequest registerUserRequest = new RegisterUserRequest(
                contributorRequest.getUsername(),
                contributorRequest.getEmail(),
                password
        );
        ResponseEntity response = userService.registerUser(registerUserRequest);
        Optional<Project> project = projectRepository.findByRepositoryName(contributorRequest.getRepositoryName());
        UserDTO user = userService.getUser(registerUserRequest.getUsername());
        projectRepository.addContributor((int) user.getId(), project.get().getId());

        StringBuilder message = new StringBuilder("");
        message.append("Hello " + registerUserRequest.getUsername() + ",\n\n");
        message.append("Welcome to GitColab!\n");
        message.append("Your username: " + registerUserRequest.getUsername() + "\n");
        message.append("Your password: " + registerUserRequest.getPassword() + "\n\n");
        message.append("Please change the password while logging first time.\n\n");
        message.append("Team GitColab!");
        emailSender.sendEmail(registerUserRequest.getEmail(),
                "Welcome to GitColab",
                String.valueOf(message));
        return ResponseEntity.ok().body(new MessageResponse("Contributor added successfully"));
    }

    public ResponseEntity<?> getProjectContributorMap(int userId, int level) {
        if (level <= 0)
            return ResponseEntity.ok().body(new MessageResponse("Invalid level"));

        List<Map<String, Object>> projectContributorMap = projectRepository.getProjectContributorMap();
        Map<Integer, Set<Integer>> adjacencyList = generateAdjacencyList(projectContributorMap);
        Set<Map<String, Object>> projectList = new HashSet<>();
        Set<Integer> contributors = new HashSet<>();
        if (!adjacencyList.containsKey(userId))
            ResponseEntity.ok().body(contributors);

        Queue<Integer> queue = new LinkedList<>();
        Map<Integer, Integer> distances = new HashMap<>();
        Map<Integer, Integer> parents = new HashMap<>();

        queue.offer(userId);
        distances.put(userId, 0);
        parents.put(userId, null);

        while (!queue.isEmpty()) {
            int curr = queue.poll();
            int currentDistance = distances.get(curr);
            if (currentDistance > level) break;

            for (int neighbor : adjacencyList.getOrDefault(curr, new HashSet<>())) {
                if (!distances.containsKey(neighbor)) {
                    queue.offer(neighbor);
                    distances.put(neighbor, currentDistance + 1);
                    parents.put(neighbor, curr);
                }
            }
        }

        for (Integer collaborator : distances.keySet()) {
            if (!collaborator.equals(userId) && distances.get(collaborator) <= level) {
                projectRepository.getAllProject(Long.valueOf(collaborator))
                        .forEach(project -> {
                            projectList.add(project);
                        });
            }
        }

        return ResponseEntity.ok().body(projectList);
    }

    public ResponseEntity<?> createJira(GithubIssueEventRequest githubIssueEventRequest) {
        String[] githubRepoSegments = githubIssueEventRequest.getRepository().getFull_name().split("/");
        String githubUserName = githubRepoSegments[0];
        String repositoryName = githubRepoSegments[1];
        Optional<ToolTokenManager> toolTokenManager = toolTokenManagerRepository.getByRepositoryOwner(githubUserName, EnumIntegrationType.ATLASSIAN);
        logger.info("Data From Tool Token Manager->" + toolTokenManager);
        logger.info(toolTokenManager.toString());
        Optional<Project> project = projectRepository.getProjectByRepositoryNameAndOwner(repositoryName, githubUserName);
        logger.info("Data From Project -> " + project);
        if (!toolTokenManager.isEmpty()) {
            String atlassianToken = "Bearer " + toolTokenManager.get().getToken();
            atlassianService.createIssue(githubIssueEventRequest, project.get().getAtlassianProjectId(), atlassianToken);
        }
        return null;
    }

    private Map<Integer, Set<Integer>> generateAdjacencyList(List<Map<String, Object>> projectContributorMap) {
        Map<Integer, Set<Integer>> adjacencyList = new HashMap<>();
        for (Map<String, Object> project : projectContributorMap) {
            int projectId = (int) project.get("projectId");
            int contributorId = (int) project.get("userId");

            if (!adjacencyList.containsKey(projectId)) {
                adjacencyList.put(projectId, new HashSet<>());
            }
            adjacencyList.get(projectId).add(contributorId);
        }
        return adjacencyList;
    }


    public ResponseEntity<?> getDashboardData(long userId) {
        Map<String, Object> dashboardData = new HashMap<>();
        List<Map<String, Object>> allProjects = projectRepository.getAllProject(userId);
        AtomicInteger totalProjectOwnership = new AtomicInteger();
        AtomicInteger totalProjectContributions = new AtomicInteger();
        allProjects.stream().forEach(stringObjectMap -> {
            AtomicInteger userId1 = new AtomicInteger(((int) stringObjectMap.get("userId") == (int) userId) ?
                    totalProjectOwnership.getAndIncrement() : totalProjectContributions.getAndIncrement());
        });
        dashboardData.put("totalProjectOwnership", totalProjectOwnership);
        dashboardData.put("totalProjectContributions", totalProjectContributions);
        String githubAuthToken = projectRepository.getGithubTokenByUserId(userId);
        GitHub gitHub = githubService.getGithubUserByToken(githubAuthToken);
        try {
            dashboardData.put("numberOfFollowers", gitHub.getMyself().getFollowersCount());
            dashboardData.put("totalRepositories", gitHub.getMyself().getPublicRepoCount());
            dashboardData.put("topCommittedRepositories", githubService.topCommittedRepositories(gitHub.getMyself(), 10));
        } catch (Exception e) {
            dashboardData.put("numberOfFollowers", 0);
            dashboardData.put("totalRepositories", 0);
            dashboardData.put("topCommittedRepositories", "");
        }
        return ResponseEntity.ok().body(dashboardData);
    }

    public ResponseEntity<?> sendProjectRequest(SendProjectRequest sendProjectRequest, String username) {
        if (!HelperUtils.isValidString(sendProjectRequest.getProjectOwner()) || !HelperUtils.isValidString(username))
            return ResponseEntity.badRequest().body(new MessageResponse("Invalid request"));

        UserDTO user = userService.getUser(sendProjectRequest.getProjectOwner());
        if(user == null)
            return ResponseEntity.badRequest().body(new MessageResponse("Something went wrong!"));

        StringBuilder message = new StringBuilder("");
        message.append("Hello " + sendProjectRequest.getProjectOwner() + ",\n\n");
        message.append("One developer interested in your project \n\n");
        message.append("Repository/Project Name: " + sendProjectRequest.getRepositoryName() + "\n");
        message.append("Developer username: " + username + "\n\n");
        message.append("Please allow access to them if you are interested.\n\n");
        message.append("Team GitColab!");


        emailSender.sendEmail(user.getEmail(),
                "Project invitation request pending! | Gitcolab",
                String.valueOf(message));
        return ResponseEntity.ok().body(new MessageResponse("Invitation sent successfully"));
    }
}
