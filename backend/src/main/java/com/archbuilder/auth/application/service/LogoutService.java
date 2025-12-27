package com.archbuilder.auth.application.service;

import com.archbuilder.auth.application.usecase.LogoutUseCase;
import com.archbuilder.auth.domain.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutUseCase {
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public void execute(String userId) {
        log.info("로그아웃 요청: userId={}", userId);
        refreshTokenRepository.deleteByUserId(userId);
        log.info("로그아웃 완료: userId={}", userId);
    }
}
