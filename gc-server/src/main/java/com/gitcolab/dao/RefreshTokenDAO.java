package com.gitcolab.dao;

import com.gitcolab.entity.RefreshToken;
import com.gitcolab.entity.User;

import java.util.Optional;

public interface RefreshTokenDAO extends DAO{
    Optional<RefreshToken> findByToken(String token);
    void deleteByUser(User user);
    void delete(RefreshToken t);
}
