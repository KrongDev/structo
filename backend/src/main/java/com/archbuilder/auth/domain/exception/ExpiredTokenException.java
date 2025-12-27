package com.archbuilder.auth.domain.exception;

import com.archbuilder.common.exception.model.DomainException;

public class ExpiredTokenException extends DomainException {
    public ExpiredTokenException() {
        super(
                "EXPIRED_TOKEN",
                "토큰이 만료되었습니다.");
    }
}