package com.archbuilder.auth.infra.persistence;

import com.archbuilder.auth.domain.model.RefreshToken;
import com.archbuilder.auth.domain.repository.RefreshTokenRepository;
import com.archbuilder.auth.infra.persistence.entity.RefreshTokenEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepositoryImpl implements RefreshTokenRepository {

    private final RefreshTokenJpaRepository refreshTokenJpaRepository;

    @Override
    public RefreshToken save(RefreshToken refreshToken) {
        return refreshTokenJpaRepository.save(new RefreshTokenEntity(refreshToken)).toDomain();
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenJpaRepository.findByToken(token).map(RefreshTokenEntity::toDomain);
    }

    @Override
    public Optional<RefreshToken> findByUserId(String userId) {
        return refreshTokenJpaRepository.findByUserId(userId).map(RefreshTokenEntity::toDomain);
    }

    @Override
    public void deleteByUserId(String userId) {
        refreshTokenJpaRepository.deleteByUserId(userId);
    }

    @Override
    public void deleteByToken(String token) {
        refreshTokenJpaRepository.deleteByToken(token);
    }
}
