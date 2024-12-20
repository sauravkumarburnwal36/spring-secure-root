package com.example.SecurityApp.SecurityApplication.repositories;

import com.example.SecurityApp.SecurityApplication.entities.SessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<SessionEntity,Long> {
    Optional<SessionEntity> findByUserId(Long userId);

    Optional<SessionEntity> findByToken(String token);

    void deleteByUserId(Long userId);

    boolean existsByUserId(Long userId);
}
