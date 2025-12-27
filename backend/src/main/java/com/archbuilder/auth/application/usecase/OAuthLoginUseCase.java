package com.archbuilder.auth.application.usecase;

import com.archbuilder.auth.application.dto.AuthResult;
import com.archbuilder.auth.application.dto.OAuthUserInfo;

public interface OAuthLoginUseCase {
    AuthResult execute(OAuthUserInfo oAuthUserInfo);
}