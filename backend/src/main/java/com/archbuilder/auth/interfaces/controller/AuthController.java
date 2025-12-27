package com.archbuilder.auth.interfaces.controller;

import com.archbuilder.auth.application.dto.AuthResult;
import com.archbuilder.auth.application.usecase.LogoutUseCase;
import com.archbuilder.auth.application.usecase.RefreshTokenUseCase;
import com.archbuilder.auth.interfaces.controller.dto.RefreshTokenRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<AuthResult> refresh(@RequestBody RefreshTokenRequest request) {
        AuthResult result = refreshTokenUseCase.execute(request.refreshToken());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@AuthenticationPrincipal String userId) {
        logoutUseCase.execute(userId);
        return ResponseEntity.noContent().build();
    }
}
