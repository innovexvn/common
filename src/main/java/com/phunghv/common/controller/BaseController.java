package com.phunghv.common.controller;

import com.phunghv.common.dto.PageApiRS;
import com.phunghv.common.dto.PageResult;
import com.phunghv.common.dto.SingleApiRS;

public class BaseController {
    protected <T> SingleApiRS<T> ok(T data) {
        return new SingleApiRS<>(data);
    }

    protected <T> PageApiRS<T> paging(PageResult<T> pageResult) {
        return new PageApiRS<>(pageResult);
    }
}
