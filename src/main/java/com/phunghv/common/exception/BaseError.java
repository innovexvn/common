package com.phunghv.common.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BaseError {
    private String title;
    private String code;
}
