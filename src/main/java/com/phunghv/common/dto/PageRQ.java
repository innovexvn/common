package com.phunghv.common.dto;

import lombok.Setter;

@Setter
public class PageRQ {
    private int page = 0;
    private int size = 10;

    public int getPage() {
        return Math.max(page, 0);
    }

    public int getSize() {
        if (size < 1) {
            return 1;
        }
        return Math.min(size, 100);
    }
}
