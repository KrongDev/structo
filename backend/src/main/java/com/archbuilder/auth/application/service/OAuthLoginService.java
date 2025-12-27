package com.archbuilder.auth.application.service;

import com.archbuilder.auth.application.dto.AuthResult;
import com.archbuilder.auth.application.dto.OAuthUserInfo;
import com.archbuilder.auth.application.usecase.OAuthLoginUseCase;
import com.archbuilder.auth.domain.model.RefreshToken;
import com.archbuilder.auth.domain.repository.RefreshTokenRepository;
import com.archbuilder.common.security.JwtTokenProvider;
import com.archbuilder.user.application.command.CreateUserCommand;
import com.archbuilder.user.application.usecase.CreateUserUseCase;
import com.archbuilder.user.application.usecase.GetUserUseCase;
import com.archbuilder.user.domain.model.User;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuthLoginService implements OAuthLoginUseCase {
    private final GetUserUseCase getUserUseCase;
    private final CreateUserUseCase createUserUseCase;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenProvider jwtTokenProvider;
    
    @Override
    @Transactional
    public AuthResult execute(OAuthUserInfo oAuthUserInfo) {
        log.info("OAuth 로그인 시도: email={}, provider={}", 
            oAuthUserInfo.email(), oAuthUserInfo.provider());

        User user = getUserUseCase.findByEmail(oAuthUserInfo.email())
            .orElseGet(() -> {
                log.info("새로운 사용자 생성: {}", oAuthUserInfo.email());
                return createUserUseCase.execute(
                    new CreateUserCommand(
                        oAuthUserInfo.email(),
                        oAuthUserInfo.name(),
                        oAuthUserInfo.provider()
                    )
                );
            });
        
        String accessToken = jwtTokenProvider.createAccessToken(user.getId(), user.getPlanType(), user.getRoles());
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getId());

        refreshTokenRepository.findByUserId(user.getId())
            .ifPresent(existing -> refreshTokenRepository.deleteByToken(existing.getToken()));
        
        saveRefreshToken(user.getId(), refreshToken);
        
        log.info("로그인 성공: userId={}", user.getId());
        
        return new AuthResult(
            accessToken,
            refreshToken,
            user.getId(),
            user.getEmail(),
            user.getName()
        );
    }
    
    private void saveRefreshToken(String userId, String token) {
        Claims claims = jwtTokenProvider.getClaims(token);
        RefreshToken refreshToken = RefreshToken.of(
            token,
            userId,
            LocalDateTime.ofInstant(
                claims.getExpiration().toInstant(),
                ZoneId.systemDefault()
            ),
            LocalDateTime.ofInstant(
                claims.getIssuedAt().toInstant(),
                ZoneId.systemDefault()
            )
        );
        
        refreshTokenRepository.save(refreshToken);
    }
}