package com.phunghv.common.dto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class PageApiRS<T> extends ResponseEntity<BaseRS<PageResult<T>>> {
    public PageApiRS() {
        super(HttpStatus.OK);
    }

    public PageApiRS(PageResult<T> pageResult) {
        super(new BaseRS<>(pageResult, HttpStatus.OK.value(), "success"), HttpStatus.OK);
    }
}
