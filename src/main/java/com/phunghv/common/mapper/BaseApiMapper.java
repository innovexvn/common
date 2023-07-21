package com.phunghv.common.mapper;

public interface BaseApiMapper<R, D> {
    R toResponse(D dto);

    D toDto(R req);
}
