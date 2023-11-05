package com.gitcolab.utilities.mappers;

import com.gitcolab.entity.EnumIntegrationType;
import com.gitcolab.entity.ToolTokenManager;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class IntegrationRowMapper implements RowMapper<ToolTokenManager> {
    @Override
    public ToolTokenManager mapRow(ResultSet rs, int rowNum) throws SQLException {
        ToolTokenManager integration = ToolTokenManager.builder()
                .id(rs.getInt("id"))
                .type(EnumIntegrationType.valueOf(rs.getString("type")))
                .token(rs.getString("token"))
                .userId(rs.getInt("userId"))
                .build();

        return integration;
    }
}