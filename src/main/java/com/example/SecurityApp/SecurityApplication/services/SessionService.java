package com.example.SecurityApp.SecurityApplication.services;

import com.example.SecurityApp.SecurityApplication.entities.SessionEntity;
import com.example.SecurityApp.SecurityApplication.repositories.SessionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SessionService {

    @Autowired
    private SessionRepository sessionRepository;

    @Transactional
    public void createSession(Long userId, String token) {
        if(sessionRepository!=null&&sessionRepository.existsByUserId(userId))
        {
            sessionRepository.deleteByUserId(userId);
        }
        SessionEntity sessionEntity=new SessionEntity(userId,token);
        sessionRepository.save(sessionEntity);
    }

    public boolean isValidToken(String token) {
        Optional<SessionEntity> session=sessionRepository.findByToken(token);
        return session.isPresent();
    }

    @Transactional
    public void removeSession(Long userId) {
        sessionRepository.deleteByUserId(userId);
    }
}
