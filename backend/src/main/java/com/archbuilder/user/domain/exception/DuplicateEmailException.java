package com.archbuilder.user.domain.exception;

import com.archbuilder.common.exception.model.DomainException;

public class DuplicateEmailException extends DomainException {
    public DuplicateEmailException(String email) {
        super(
            "USER_DUPLICATE_EMAIL",
            "이미 사용 중인 이메일입니다: " + email
        );
    }
}
