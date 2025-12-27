package com.archbuilder.auth.application.usecase;

import com.archbuilder.auth.application.dto.AuthResult;

public interface RefreshTokenUseCase {
    AuthResult execute(String refreshToken);
}