package com.archbuilder.auth.domain.exception;

import com.archbuilder.common.exception.model.DomainException;

public class InvalidTokenException extends DomainException {
    public InvalidTokenException() {
        super(
                "INVALID_TOKEN",
                "유효하지 않은 토큰입니다.");
    }
}