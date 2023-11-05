package com.gitcolab.dao;

import com.gitcolab.entity.User;

import java.util.Optional;

public interface UserDAO extends DAO  {
    Optional<User> getUserByUserName(String username);
    Optional<User> getUserByEmail(String email);

    public boolean existsByUsername(String username);

    public boolean existsByEmail(String email);

    //new method to eliminate confusion
    public int updateProfile(Object o);

}