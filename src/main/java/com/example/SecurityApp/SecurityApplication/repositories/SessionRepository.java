package com.example.SecurityApp.SecurityApplication.repositories;

import com.example.SecurityApp.SecurityApplication.entities.Session;
import com.example.SecurityApp.SecurityApplication.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface SessionRepository extends JpaRepository<Session,Long> {
    List<Session> findByUser(User user);

    Optional<Session> findByRefreshToken(String refreshToken);
}
