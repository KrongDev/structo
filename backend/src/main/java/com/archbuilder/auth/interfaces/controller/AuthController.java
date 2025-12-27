package com.archbuilder.auth.interfaces.controller;

import com.archbuilder.auth.application.dto.AuthResult;
import com.archbuilder.auth.application.usecase.LogoutUseCase;
import com.archbuilder.auth.application.usecase.RefreshTokenUseCase;
import com.archbuilder.auth.interfaces.controller.dto.RefreshTokenRequest;
import com.archbuilder.common.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final RefreshTokenUseCase refreshTokenUseCase;
    private final LogoutUseCase logoutUseCase;

    @PostMapping("/refresh")
    public ApiResponse<AuthResult> refresh(@RequestBody RefreshTokenRequest request) {
        AuthResult result = refreshTokenUseCase.execute(request.refreshToken());
        return ApiResponse.ok(result);
    }

    @PostMapping("/logout")
    public ApiResponse<Void> logout(@AuthenticationPrincipal String userId) {
        logoutUseCase.execute(userId);
        return ApiResponse.ok();
    }
}
