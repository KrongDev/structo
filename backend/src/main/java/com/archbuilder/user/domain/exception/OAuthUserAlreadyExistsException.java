package com.archbuilder.user.domain.exception;

import com.archbuilder.common.exception.model.DomainException;
import com.archbuilder.user.domain.model.vo.AuthProvider;

public class OAuthUserAlreadyExistsException extends DomainException {
    public OAuthUserAlreadyExistsException(AuthProvider provider) {
        super(
            "USER_OAUTH_ALREADY_EXISTS",
            "이미 존재하는 "+provider+" 사용자입니다"
        );
    }
}
