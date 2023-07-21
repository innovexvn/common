package com.phunghv.common.dto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class SingleApiRS<T> extends ResponseEntity<BaseRS<T>> {
    public SingleApiRS() {
        super(HttpStatus.OK);
    }

    public SingleApiRS(T data) {
        super(new BaseRS<>(data, HttpStatus.OK.value(), "success"), HttpStatus.OK);
    }
}
