package com.archbuilder.auth.application.service;

import com.archbuilder.auth.application.dto.AuthResult;
import com.archbuilder.auth.application.usecase.RefreshTokenUseCase;
import com.archbuilder.auth.domain.exception.ExpiredTokenException;
import com.archbuilder.auth.domain.exception.InvalidTokenException;
import com.archbuilder.auth.domain.model.RefreshToken;
import com.archbuilder.auth.domain.repository.RefreshTokenRepository;
import com.archbuilder.common.security.JwtTokenProvider;
import com.archbuilder.user.application.usecase.GetUserUseCase;
import com.archbuilder.user.domain.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RefreshTokenService implements RefreshTokenUseCase {
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final GetUserUseCase getUserUseCase;

    @Override
    @Transactional
    public AuthResult execute(String refreshToken) {
        log.info("토큰 갱신 요청");

        RefreshToken token = refreshTokenRepository.findByToken(refreshToken)
                .orElseThrow(InvalidTokenException::new);

        if (token.isExpired()) {
            log.warn("만료된 토큰 사용 시도: userId={}", token.getUserId());
            refreshTokenRepository.deleteByToken(refreshToken);
            throw new ExpiredTokenException();
        }

        User user = getUserUseCase.getById(token.getUserId());

        String newAccessToken = jwtTokenProvider.createAccessToken(user.getId(), user.getPlanType(), user.getRoles());

        log.info("토큰 갱신 성공: userId={}", user.getId());

        return new AuthResult(
                newAccessToken,
                refreshToken,
                user.getId(),
                user.getEmail(),
                user.getName()
        );
    }
}
