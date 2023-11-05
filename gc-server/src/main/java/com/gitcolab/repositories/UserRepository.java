package com.gitcolab.repositories;

import com.gitcolab.entity.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository {
    int save(User user);
    int update(User user);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    Optional<User> findByUsername(String username);
    Optional<User> findById(long id);
    int saveOtp(User user);
    Optional<User> getUserByEmail(String email);

    //new method to eliminate confusion
    int updateProfile(User user);
}
