package com.gitcolab.repositories;

import com.gitcolab.dao.ToolTokenManagerDAO;
import com.gitcolab.entity.EnumIntegrationType;
import com.gitcolab.entity.ToolTokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ToolTokenManagerRepositoryImplementation implements ToolTokenManagerRepository {

    @Autowired
    ToolTokenManagerDAO toolTokenDAO;

    @Override
    public int save(ToolTokenManager integration) {
        return toolTokenDAO.save(integration);
    }

    @Override
    public int update(ToolTokenManager integration) {
        return toolTokenDAO.update(integration);
    }

    @Override
    public Optional<ToolTokenManager> getByEmail(String email, EnumIntegrationType type) {
        return toolTokenDAO.getByEmail(email,type);
    }

    @Override
    public Optional<ToolTokenManager> getByUsername(String username) {
        return toolTokenDAO.getByUsername(username);
    }

    @Override
    public Optional<ToolTokenManager> getByRepositoryOwner(String repositoryOwner, EnumIntegrationType type) { return toolTokenDAO.getByRepositoryOwner(repositoryOwner, type); }
}
