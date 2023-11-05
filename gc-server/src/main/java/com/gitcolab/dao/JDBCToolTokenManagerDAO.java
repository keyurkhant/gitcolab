package com.gitcolab.dao;

import com.gitcolab.entity.EnumIntegrationType;
import com.gitcolab.entity.ToolTokenManager;
import com.gitcolab.utilities.mappers.IntegrationRowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.AbstractSqlParameterSource;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JDBCToolTokenManagerDAO implements ToolTokenManagerDAO {
    private static Logger logger= LoggerFactory.getLogger(JDBCToolTokenManagerDAO.class);
    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Optional get(long id) {
        ToolTokenManager integration = jdbcTemplate.queryForObject("select * from ToolTokenRegistry i where i.id=?",new Object[]{id}, new IntegrationRowMapper());
        return Optional.of(integration);
    }

    @Override
    public int save(ToolTokenManager integration) {
        AbstractSqlParameterSource namedParameters = new BeanPropertySqlParameterSource(integration);
        namedParameters.registerTypeName("typeString", integration.getType().toString());
        return namedParameterJdbcTemplate
                .update("INSERT INTO ToolTokenRegistry(`type`,`token`,`userId`) values(:typeString,:token,:userId)"
                        ,namedParameters);
    }

    @Override
    public int update(ToolTokenManager integration) {
        AbstractSqlParameterSource namedParameters = new BeanPropertySqlParameterSource(integration);
        namedParameters.registerTypeName("typeString", integration.getType().toString());
        return namedParameterJdbcTemplate
                .update("UPDATE ToolTokenRegistry SET `token` = :token WHERE `userId` = :userId and `type` = :typeString"
                        ,namedParameters);
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public Optional<ToolTokenManager> getByEmail(String email, EnumIntegrationType integrationType) {
        ToolTokenManager integration;
        try {
            integration = jdbcTemplate
                    .queryForObject(
                            "select * from ToolTokenRegistry i where i.userId = (Select id from User u where u.email = ?) AND i.type=?"
                    ,new Object[]{email,integrationType.toString()}
                    , new IntegrationRowMapper());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Optional.empty();
        }
        return Optional.of(integration);
    }

    @Override
    public Optional<ToolTokenManager> getByUsername(String username) {
        return null;
    }

    @Override
    public Optional<ToolTokenManager> getByRepositoryOwner(String repositoryOwner, EnumIntegrationType type) {
        ToolTokenManager integration;
        try {
            integration = jdbcTemplate
                    .queryForObject(
                            "select * from ToolTokenRegistry i where i.userId = (select userId from Project p where p.repositoryOwner=? limit 1) and i.type=? order by 1 desc limit 1"
                            ,new Object[]{repositoryOwner,type.toString()}
                            , new IntegrationRowMapper());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Optional.empty();
        }
        return Optional.of(integration);
    }
}
