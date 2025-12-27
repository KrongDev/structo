package com.archbuilder.auth.application.dto;

public record AuthResult(
        String accessToken,
        String refreshToken,
        String userId,
        String email,
        String name
) {}