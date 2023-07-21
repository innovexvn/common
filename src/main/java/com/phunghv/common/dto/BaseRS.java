package com.phunghv.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BaseRS<T> {
    private T data;
    private int status;
    private String message;
}
