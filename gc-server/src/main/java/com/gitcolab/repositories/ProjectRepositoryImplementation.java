package com.gitcolab.repositories;

import com.gitcolab.dao.ProjectDAO;
import com.gitcolab.entity.Project;
import com.gitcolab.entity.RefreshToken;
import com.gitcolab.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProjectRepositoryImplementation implements ProjectRepository {
    @Autowired
    ProjectDAO projectDAO;
    @Override
    public int save(Project project) {
        return projectDAO.save(project);
    }

    @Override
    public int update(Project project) {
        return projectDAO.update(project);
    }

    @Override
    public void delete(RefreshToken t) {

    }

    @Override
    public List<Map<String, Object>> getAllProject(Long id) {
        return projectDAO.getAllProjects(id);
    }

    @Override
    public Optional<Project> findById(int id) {
        return projectDAO.get(id);
    }

    @Override
    public List<Map<String, Object>> getAllContributors(int projectId) {
        return projectDAO.getAllContributors(projectId);
    }

    @Override
    public int addContributor(int userId, int projectId) {
        return projectDAO.addContributor(userId, projectId);
    }

    @Override
    public Optional<Project> findByRepositoryName(String repositoryName) {
        return projectDAO.getProjectByRepositoryName(repositoryName);
    }

    @Override
    public List<Map<String, Object>> getProjectContributorMap() {
        return projectDAO.getProjectContributorMap();
    }

    @Override
    public String getGithubTokenByUserId(Long id) {
        return projectDAO.getGithubTokenByUserId(id);
    }

    @Override
    public boolean isJiraBoardExist(String jiraBoardName, String userId) {
        return false;
    }

    @Override
    public Optional<Project> getProjectByRepositoryNameAndOwner(String repositoryName, String repositoryOwner) {
        return projectDAO.getProjectByRepositoryNameAndOwner(repositoryName,repositoryOwner);
//        return Optional.empty();
    }
//    Optional<Project> getProjectByRepositoryNameAndOwner();
}
