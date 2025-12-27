package com.archbuilder.common.exception.model;

import lombok.Getter;

@Getter
public abstract class DomainException extends RuntimeException {
    private final String errorCode;

    protected DomainException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

}