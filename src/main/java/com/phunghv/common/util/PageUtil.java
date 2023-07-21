package com.phunghv.common.util;

import com.phunghv.common.dto.PageResult;
import org.springframework.data.domain.Page;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PageUtil extends cn.hutool.core.util.PageUtil {

    public static <T> List<T> paging(int page, int size, List<T> list) {
        int fromIndex = page * size;
        int toIndex = page * size + size;
        if (fromIndex > list.size()) {
            return Collections.emptyList();
        } else if (toIndex >= list.size()) {
            return list.subList(fromIndex, list.size());
        } else {
            return list.subList(fromIndex, toIndex);
        }
    }

    public static <T> PageResult<T> toPage(Page<T> page) {
        return new PageResult<>(page.getContent(), page.getTotalElements());
    }

    public static <T, O> PageResult<T> toPage(Page<O> page, Function<O, T> function) {
        var items = page.getContent().stream().map(function).collect(Collectors.toList());
        return new PageResult<>(items, page.getTotalElements());
    }

    public static <T> PageResult<T> toPage(List<T> list, long totalElements) {
        return new PageResult<>(list, totalElements);
    }

    public static <T> PageResult<T> noData() {
        return new PageResult<>(Collections.emptyList(), 0);
    }
}