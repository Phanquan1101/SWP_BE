package com.crowdsourced.wasteplatform.mapper;

import com.crowdsourced.wasteplatform.exception.PageResponse;
import org.springframework.data.domain.Page;

public class PageMapper {

    public static <T> PageResponse<T> toPageResponse(Page<T> page) {
        return new PageResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.hasNext(),
                page.hasPrevious()
        );
    }
}

