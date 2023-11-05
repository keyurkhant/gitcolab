package com.gitcolab.utilities.mappers;

import com.gitcolab.entity.Project;
import com.gitcolab.entity.RefreshToken;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProjectRowMapper implements RowMapper<Project> {
    @Override
    public Project mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Project project = new Project();
        project.setId(resultSet.getInt("id"));
        project.setUserId(resultSet.getInt("userId"));
        project.setRepositoryName(resultSet.getString("repositoryName"));
        project.setRepositoryOwner(resultSet.getString("repositoryOwner"));
        project.setAtlassianProjectId(resultSet.getString("atlassianProjectId"));
        project.setJiraBoardName(resultSet.getString("jiraBoardName"));
        project.setTimestamp(resultSet.getTimestamp("timestamp").toInstant());
        return project;
    }
}
