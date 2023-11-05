package com.gitcolab.repositories;


import com.gitcolab.entity.EnumIntegrationType;
import com.gitcolab.entity.ToolTokenManager;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ToolTokenManagerRepository {
    int save(ToolTokenManager integration);
    int update(ToolTokenManager integration);

    Optional<ToolTokenManager> getByEmail(String email, EnumIntegrationType type);

    public Optional<ToolTokenManager> getByUsername(String username);
    public Optional<ToolTokenManager> getByRepositoryOwner(String repositoryOwner,EnumIntegrationType type);

}
