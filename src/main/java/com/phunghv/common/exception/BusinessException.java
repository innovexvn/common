package com.phunghv.common.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    private final BaseError error;

    public BusinessException(final BaseError error) {
        this.error = error;
    }

    public BusinessException(final BaseError error, Throwable throwable) {
        super(throwable);
        this.error = error;
    }
}
