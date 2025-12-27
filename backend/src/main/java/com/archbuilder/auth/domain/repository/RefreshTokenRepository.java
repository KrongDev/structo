package com.archbuilder.auth.domain.repository;

import com.archbuilder.auth.domain.model.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository {
    RefreshToken save(RefreshToken refreshToken);
    Optional<RefreshToken> findByToken(String token);
    Optional<RefreshToken> findByUserId(String userId);
    void deleteByUserId(String userId);
    void deleteByToken(String token);
}