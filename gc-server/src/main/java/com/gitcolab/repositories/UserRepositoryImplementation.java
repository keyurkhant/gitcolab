package com.gitcolab.repositories;

import com.gitcolab.dao.UserDAO;
import com.gitcolab.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserRepositoryImplementation implements UserRepository{
    @Autowired
    UserDAO userDao;

    @Override
    public int save(User user) {
        return userDao.save(user);
    }

    @Override
    public int update(User user) { return userDao.update(user); }

    @Override
    public boolean existsByUsername(String username) {
        return userDao.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userDao.existsByEmail(email);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userDao.getUserByUserName(username);
    }

    @Override
    public Optional<User> findById(long id) {
        return userDao.get(id);
    }

    @Override
    public int saveOtp(User user) { return userDao.update(user); }

    @Override
    public Optional<User> getUserByEmail(String email) { return  userDao.getUserByEmail(email); }

    //new method to eliminate confusion
    @Override
    public int updateProfile(User user) { return userDao.updateProfile(user); }

}
