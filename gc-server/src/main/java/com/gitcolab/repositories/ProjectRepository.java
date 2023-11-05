package com.gitcolab.repositories;

import com.gitcolab.entity.Project;
import com.gitcolab.entity.RefreshToken;
import com.gitcolab.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface ProjectRepository {
    int save(Project project);
    int update(Project project);
    Optional<Project> findById(int id);
    Optional<Project> findByRepositoryName(String repositoryName);
    void delete(RefreshToken t);
    List<Map<String, Object>> getAllProject(Long id);
    List<Map<String, Object>> getAllContributors(int projectId);
    int addContributor(int userId, int projectId);
    List<Map<String, Object>> getProjectContributorMap();
    String getGithubTokenByUserId(Long id);
    boolean isJiraBoardExist(String jiraBoardName,String userId);

    Optional<Project> getProjectByRepositoryNameAndOwner(String repositoryName,String repositoryOwner);
}
