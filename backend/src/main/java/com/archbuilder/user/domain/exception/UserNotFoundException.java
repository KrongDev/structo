package com.archbuilder.user.domain.exception;

import com.archbuilder.common.exception.model.DomainException;

public class UserNotFoundException extends DomainException {
    public UserNotFoundException(String id) {
        super(
            "USER_NOT_FOUND",
            "유저가 존재하지 않습니다: " + id
        );
    }
}
