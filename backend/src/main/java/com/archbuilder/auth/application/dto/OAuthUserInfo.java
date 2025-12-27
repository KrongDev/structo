package com.archbuilder.auth.application.dto;

import com.archbuilder.user.domain.model.vo.AuthProvider;

public record OAuthUserInfo(
    String email,
    String name,
    AuthProvider provider
) {}