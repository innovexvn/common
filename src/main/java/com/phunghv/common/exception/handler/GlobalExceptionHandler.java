package com.phunghv.common.exception.handler;

import com.phunghv.common.exception.BusinessException;
import com.phunghv.common.exception.ErrorCode;
import com.phunghv.common.exception.UnauthorizedException;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = UnauthorizedException.class)
    public ResponseEntity<ApiError> unauthorizedException(UnauthorizedException e) {
        log.error("unauthorized exception", e);
        var error = e.getError();
        return buildResponseEntity(ApiError.error(HttpStatus.UNAUTHORIZED.value(), error.getCode(), error.getTitle()));
    }

    @ExceptionHandler(value = BusinessException.class)
    public ResponseEntity<ApiError> businessException(BusinessException e) {
        log.error("Business exception", e);
        var error = e.getError();
        return buildResponseEntity(ApiError.error(HttpStatus.BAD_REQUEST.value(), error.getCode(), error.getTitle()));
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ApiError> handleException(Throwable e) {
        log.error("general exception", e);
        return buildResponseEntity(ApiError.error(ErrorCode.GENERAL_ERROR_CODE, e.getMessage()));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiError> badCredentialsException(BadCredentialsException e) {
        String message = e.getMessage();
        log.error(message, e);
        return buildResponseEntity(ApiError.error(ErrorCode.GENERAL_ERROR_CODE, message));
    }

    @ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseEntity<ApiError> entityNotFoundException(EntityNotFoundException e) {
        log.error("Entity not found", e);
        return buildResponseEntity(ApiError.error(HttpStatus.NOT_FOUND.value(), ErrorCode.GENERAL_ERROR_CODE, e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("method argument is not valid", e);
        ObjectError objectError = e.getBindingResult().getAllErrors().get(0);
        String message = objectError.getDefaultMessage();
        if (objectError instanceof FieldError error) {
            message = error.getField() + ": " + message;
        }
        return buildResponseEntity(ApiError.error(ErrorCode.GENERAL_ERROR_CODE, message));
    }

    private ResponseEntity<ApiError> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, HttpStatus.valueOf(apiError.getStatus()));
    }
}
