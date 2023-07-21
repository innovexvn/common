package com.phunghv.common.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;


@Getter
@RequiredArgsConstructor
public class PageResult<T> {
    private final List<T> items;

    private final long total;
}