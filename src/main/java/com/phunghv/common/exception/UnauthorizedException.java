package com.phunghv.common.exception;

public class UnauthorizedException extends BusinessException {
    public UnauthorizedException() {
        super(new BaseError("Unauthorized", ErrorCode.UNAUTHORIZED_ERROR_CODE));
    }
}
