package com.gitcolab.dao;

import com.gitcolab.entity.EnumIntegrationType;
import com.gitcolab.entity.ToolTokenManager;

import java.util.Optional;

public interface ToolTokenManagerDAO extends DAO<ToolTokenManager> {

    public Optional<ToolTokenManager> getByEmail(String email, EnumIntegrationType type);

    public Optional<ToolTokenManager> getByUsername(String username);

    public Optional<ToolTokenManager> getByRepositoryOwner(String repositoryOwner, EnumIntegrationType type);
}
