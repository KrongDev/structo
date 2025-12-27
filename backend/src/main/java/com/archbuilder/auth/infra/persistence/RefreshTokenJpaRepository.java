package com.archbuilder.auth.infra.persistence;

import com.archbuilder.auth.infra.persistence.entity.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenJpaRepository extends JpaRepository<RefreshTokenEntity, String> {
    Optional<RefreshTokenEntity> findByToken(String token);
    Optional<RefreshTokenEntity> findByUserId(String userId);
    void deleteByUserId(String userId);
    void deleteByToken(String token);
}
