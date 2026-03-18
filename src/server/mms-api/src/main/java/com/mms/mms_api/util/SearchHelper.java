package com.mms.mms_api.util;

import java.util.List;
import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;

import com.mms.mms_api.common.PaginatedResult;

public class SearchHelper {
    private SearchHelper() {
    }

    @NonNull
    public static Pageable generatePageable(int pageNumber, int pageSize) {
        return PageRequest.of(pageNumber - 1, pageSize);
    }

    public static <T, R> PaginatedResult<R> generatePaginatedResult(Page<T> page, Function<T, R> mapFunction) {
        List<R> mappedContent = page.getContent().stream()
                .map(mapFunction)
                .toList();

        return new PaginatedResult<>(mappedContent, page.getTotalElements(), page.getTotalPages(),
                page.getSize(), page.getNumber() + 1);
    }
}