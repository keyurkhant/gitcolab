package com.gitcolab.repositories;

import com.gitcolab.entity.RefreshToken;
import com.gitcolab.entity.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository {
    int save(RefreshToken refreshToken);
    Optional<RefreshToken> findByToken(String token);
    void deleteByUser(User user);
    void delete(RefreshToken t);

}
