package com.gitcolab.dao;

import com.gitcolab.entity.Project;
import com.gitcolab.utilities.mappers.ProjectRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class JDBCProjectDAO implements ProjectDAO {
    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    JdbcTemplate jdbcTemplate;


    @Override
    public Optional get(long id) {
        Project project=jdbcTemplate.queryForObject("select * from Project p where p.id=?",new Object[]{id}, new ProjectRowMapper());
        return Optional.of(project);
    }

    @Override
    public int save(Object o) {
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(o);
        return namedParameterJdbcTemplate
                .update("INSERT INTO Project(`userId`,`repositoryName`,`repositoryOwner`,`atlassianProjectId`,`jiraBoardName`,`timestamp`) " +
                                "values(:userId,:repositoryName,:repositoryOwner,:atlassianProjectId,:jiraBoardName,:timestamp)"
                        ,namedParameters);
    }

    @Override
    public int update(Object o) {
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(o);
        return namedParameterJdbcTemplate
                .update("UPDATE Project SET `userId`=:userId,`repositoryName`=:repositoryName,`repositoryOwner`=:repositoryOwner,`atlassianProjectId`=:atlassianProjectId, `jiraBoardName`=:jiraBoardName, `timestamp`=:timestamp WHERE `id` = :id"
                        ,namedParameters);
    }

    @Override
    public void delete(long id) {
    }

    @Override
    public List<Map<String, Object>> getAllProjects(Long id) {
        List<Map<String, Object>> projects = jdbcTemplate.queryForList("select * from Project p join Contributors c on c.projectId = p.id where c.userId=?",new Object[]{id});
        return projects;
    }

    @Override
    public List<Map<String, Object>> getAllContributors(int id) {
        List<Map<String, Object>> projects = jdbcTemplate.queryForList( "select * from User u join Contributors c on u.id = c.userId where c.projectId = ?",new Object[]{id});
        return projects;
    }

    @Override
    public int addContributor(int userId, int projectId) {
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(userId);
        return namedParameterJdbcTemplate
                .update("INSERT INTO Contributors (`userId`,`projectId`) VALUES (" + userId + "," + projectId + ")"
                        ,namedParameters);
    }

    @Override
    public Optional<Project> getProjectByRepositoryName(String repositoryName) {
        Project project=jdbcTemplate.queryForObject("select * from Project p where p.repositoryName=?",new Object[]{repositoryName}, new ProjectRowMapper());
        return Optional.of(project);
    }

    @Override
    public Optional<Project> getProjectByRepositoryNameAndOwner(String repositoryName, String repositoryOwner) {
        Project project=jdbcTemplate.queryForObject("select * from Project p where p.repositoryName=? and p.repositoryOwner=?",new Object[]{repositoryName,repositoryOwner}, new ProjectRowMapper());
        return Optional.of(project);
    }

    @Override
    public List<Map<String, Object>> getProjectContributorMap() {
        List<Map<String, Object>> projectContributorMap = jdbcTemplate.queryForList(
                "SELECT p.id as projectId, p.userId as repositoryOwnerId, \n" +
                "p.repositoryName, p.repositoryOwner, c.userId, \n" +
                "u.username, u.email FROM Project p\n" +
                "JOIN Contributors c\n" +
                "ON p.id = c.projectId\n" +
                "JOIN User u\n" +
                "ON u.id = c.userId;\n");
        return projectContributorMap;
    }

    @Override
    public String getGithubTokenByUserId(Long id) {
        String token = jdbcTemplate.queryForObject("SELECT token FROM ToolTokenRegistry WHERE userId = ? AND type = \"GITHUB\";", String.class, id);
        return token;
    }
}
