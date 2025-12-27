package com.archbuilder.common.exception;

import com.archbuilder.common.dto.ErrorResponse;
import com.archbuilder.common.exception.model.DomainException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Domain Exception
    @ExceptionHandler(DomainException.class)
    public ErrorResponse handleCustomException(DomainException ex) {
        log.warn(ex.getMessage(), ex);
        return ErrorResponse.of(ex.getErrorCode(),ex.getMessage());
    }

    //진짜 에러들을 캐치
    @ExceptionHandler(RuntimeException.class)
    public void handleRuntimeException(RuntimeException ex) {
        log.error(ex.getMessage(), ex);
    }
}