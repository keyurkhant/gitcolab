package com.gitcolab.services;

import com.gitcolab.dto.inhouse.request.GithubAuthRequest;
import com.gitcolab.dto.inhouse.response.GithubAuthTokenResponse;
import com.gitcolab.dto.inhouse.request.GithubRepositoryRequest;
import com.gitcolab.dto.inhouse.response.MessageResponse;
import com.gitcolab.entity.EnumIntegrationType;
import com.gitcolab.entity.ToolTokenManager;
import com.gitcolab.entity.User;
import com.gitcolab.repositories.ToolTokenManagerRepository;
import com.gitcolab.repositories.UserRepository;
import com.gitcolab.utilities.HelperUtils;
import org.kohsuke.github.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URL;
import java.util.*;

@Service
public class GithubService {
    private static Logger logger= LoggerFactory.getLogger(GithubService.class);
    ToolTokenManagerRepository toolTokenManagerRepository;
    UserRepository userRepository;
    @Value("${gitcolab.app.github.clientId}")
    private String CLIENT_ID;

    @Value("${gitcolab.app.github.clientSecret}")
    private String CLIENT_SECRET;

    @Autowired
    public GithubService(ToolTokenManagerRepository toolTokenManagerRepository, UserRepository userRepository) {
        this.toolTokenManagerRepository = toolTokenManagerRepository;
        this.userRepository = userRepository;
    }

    public ResponseEntity<?> getAccessToken(GithubAuthRequest githubAuthRequest) {
        if (githubAuthRequest.getCode() == null || githubAuthRequest.getCode().isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Github auth code is invalid."));
        }
        if(githubAuthRequest.getEmail() == null || githubAuthRequest.getEmail().isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Github auth code is invalid."));
        }

        StringBuilder githubAuthUrl = new StringBuilder("https://github.com/login/oauth/access_token");
        githubAuthUrl.append("?client_id=" + CLIENT_ID);
        githubAuthUrl.append("&client_secret=" + CLIENT_SECRET);
        githubAuthUrl.append("&code=" + githubAuthRequest.getCode());

        WebClient webClient2 = WebClient.builder()
                .baseUrl(String.valueOf(githubAuthUrl))
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
        String response = webClient2.post().retrieve().bodyToMono(String.class).block();
        if(!(response.startsWith("access_token") || response.startsWith("error"))) {
            return ResponseEntity.badRequest().body(new MessageResponse("Github authentication failed."));
        }
        if(response.startsWith("error")) {
            return ResponseEntity.badRequest().body(new MessageResponse("Github authentication failed."));
        }
        String[] splitResponse = response.split("&");
        String accessToken = splitResponse[0].split("=")[1];
        String type = splitResponse[2].split("=")[1];

        Optional<User> user = userRepository.getUserByEmail(githubAuthRequest.getEmail());
        Optional<ToolTokenManager> github = toolTokenManagerRepository.getByEmail(githubAuthRequest.getEmail(),EnumIntegrationType.GITHUB);
        if(!user.isPresent()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Github authentication failed."));
        }
        if(!github.isPresent()) {
            toolTokenManagerRepository.save(new ToolTokenManager(EnumIntegrationType.GITHUB, accessToken, user.get().getId()));
        } else {
            toolTokenManagerRepository.update(new ToolTokenManager(EnumIntegrationType.GITHUB, accessToken, user.get().getId()));
        }

        return ResponseEntity.ok(new GithubAuthTokenResponse(accessToken, type));
    }

    public ResponseEntity<?> generateRepository(GithubRepositoryRequest githubRepositoryRequest) {
        ResponseEntity sanitizationResponse = sanitizeGithubRepositoryRequest(githubRepositoryRequest);
        if(sanitizationResponse != null) return sanitizationResponse;
        try {
            GitHub github = new GitHubBuilder().withOAuthToken(githubRepositoryRequest.getGithubAccessToken()).build();
            if(getRepositoryByName(githubRepositoryRequest.getRepositoryName(),
                    githubRepositoryRequest.getGithubAccessToken()) != null) {
                return ResponseEntity.badRequest().body(new MessageResponse("Repository already exists."));
            }
            GHCreateRepositoryBuilder repo = github.createRepository(githubRepositoryRequest.getRepositoryName());
            GHRepository created = repo.create();
            return ResponseEntity.ok().body(new MessageResponse("Github repository created."));
        } catch (Exception e) {
            logger.error("Github facing issue."+e.getMessage());
            return ResponseEntity.badRequest().body(new MessageResponse("Github facing issue."));
        }
    }

    public boolean generateRepository(String name, String token) {
        try {
            GitHub github = new GitHubBuilder().withOAuthToken(token).build();
            if(getRepositoryByName(name, token) != null) {
                return false;
            }
            GHCreateRepositoryBuilder repo = github.createRepository(name);
            repo.create();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public GHRepository getRepositoryByName(String name, String token) {
        try {
            GitHub github = new GitHubBuilder().withOAuthToken(token).build();
            String repositoryFullName = github.getMyself().getLogin() + '/' + name;
            GHRepository repository = github.getRepository(repositoryFullName);
            return repository;
        } catch (Exception e) {
            return null;
        }
    }

    public GitHub getGithubUserByToken(String token) {
        try {
            return new GitHubBuilder().withOAuthToken(token).build();
        } catch (Exception e) {
            return null;
        }
    }

    public GHUser getPublicUser(String username, String token) {
        try {
            GitHub mySelf = getGithubUserByToken(token);
            return mySelf.getUser(username);
        } catch (Exception e) {
            return null;
        }
    }

    public boolean addCollaborator(String repositoryName, String username, String token) {
        try {
            GHRepository ghRepository = getRepositoryByName(repositoryName, token);
            GHUser ghUser = getPublicUser(username, token);
            Collection<GHUser> users = new ArrayList<>();
            users.add(ghUser);
            ghRepository.addCollaborators(users);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean generateWebHook(String repositoryName, String token) {
        try {
            GitHub github = new GitHubBuilder().withOAuthToken(token).build();
            String repositoryFullName = github.getMyself().getLogin() + '/' + repositoryName;
            GHRepository repository = github.getRepository(repositoryFullName);

            URL webhookURL = new URL("https://git-colab-0657fd479701.herokuapp.com/api/project/createJira");
            Collection<GHEvent> events = new ArrayList<>();
            events.add(GHEvent.ISSUES);

            HashMap<String,String> config=new HashMap<>();
            config.put("url",webhookURL.toExternalForm());
            config.put("content_type", "json");

            repository.createHook("web", Collections.singletonMap("url", webhookURL.toExternalForm()), events, true);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    private ResponseEntity<MessageResponse> sanitizeGithubRepositoryRequest(GithubRepositoryRequest githubRepositoryRequest) {
        if(githubRepositoryRequest == null) {
            return ResponseEntity.badRequest().body(new MessageResponse("Github repository request is empty"));
        }
        if(!HelperUtils.isValidString(githubRepositoryRequest.getGithubAccessToken()))
            return ResponseEntity.badRequest().body(new MessageResponse("Github authentication failed."));
        if(!HelperUtils.isValidString(githubRepositoryRequest.getRepositoryName()))
            return ResponseEntity.badRequest().body(new MessageResponse("Repository name is empty."));
        return null;
    }

    public List<RepositoryCommits> topCommittedRepositories(GHUser ghUser, int topCount) {
        try {
            List<GHRepository> repositories = ghUser.listRepositories(30).toList();

            List<RepositoryCommits> commitsList = new ArrayList<>();
            for (GHRepository repo : repositories) {
                GHCommitQueryBuilder commitQueryBuilder = repo.queryCommits().author(ghUser.getLogin());
                try {
                    PagedIterable<GHCommit> list = commitQueryBuilder.list();
                    commitsList.add(new RepositoryCommits(repo.getName(), list.toList().size()));
                } catch (Exception e){
                    commitsList.add(new RepositoryCommits(repo.getName(), 0));
                }
            }
            Collections.sort(commitsList, Comparator.comparingInt(RepositoryCommits::getCommits).reversed());

            List<RepositoryCommits> topRepositories = commitsList.subList(0, Math.min(commitsList.size(), topCount));
            return topRepositories;
        } catch (Exception e) {
            return null;
        }
    }

    public static class RepositoryCommits {
        private final String repositoryName;
        private final int commits;

        public RepositoryCommits(String repositoryName, int commits) {
            this.repositoryName = repositoryName;
            this.commits = commits;
        }

        public String getRepositoryName() {
            return repositoryName;
        }

        public int getCommits() {
            return commits;
        }
    }

}
