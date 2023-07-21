package com.phunghv.common.exception.handler;

import lombok.Data;

@Data
public class ApiError {
    private int status = 400;
    private Long timestamp;
    private String message;
    private String code;

    private ApiError() {
        timestamp = System.currentTimeMillis();
    }

    public static ApiError error(String code, String message) {
        var apiError = new ApiError();
        apiError.setMessage(message);
        apiError.setCode(code);
        return apiError;
    }

    public static ApiError error(Integer status, String code, String message) {
        var apiError = new ApiError();
        apiError.setStatus(status);
        apiError.setCode(code);
        apiError.setMessage(message);
        return apiError;
    }
}
