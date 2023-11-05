package com.gitcolab.repositories;

import com.gitcolab.dao.RefreshTokenDAO;
import com.gitcolab.entity.RefreshToken;
import com.gitcolab.entity.User;
import com.gitcolab.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RefreshTokenRepositoryImplementation implements RefreshTokenRepository{
    @Autowired
    RefreshTokenDAO refreshTokenDAO;

    @Autowired
    UserRepository userRepository;
    @Override
    public int save(RefreshToken refreshToken) {
        return refreshTokenDAO.save(refreshToken);
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        Optional<RefreshToken> refreshToken = refreshTokenDAO.findByToken(token);
        if(refreshToken.isPresent()){
            User user=refreshToken.get().getUser();
            Optional<User> completeUserData=userRepository.findById(user.getId());
            completeUserData.ifPresent(value -> refreshToken.get().setUser(value));
        }
        return refreshToken;
    }

    @Override
    public void deleteByUser(User user) {
        refreshTokenDAO.deleteByUser(user);
    }

    @Override
    public void delete(RefreshToken t) {
        refreshTokenDAO.delete(t);
    }
}
