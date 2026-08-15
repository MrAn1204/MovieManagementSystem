package com.mms.mms_api.util;

import java.util.List;
import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;

import com.mms.mms_api.common.PaginatedResult;

/**
 * Utility helpers for pageable requests and paginated responses.
 */
public class SearchHelper {
    private SearchHelper() {
    }

    /**
     * Creates a pageable instance from one-based page input.
     */
    @NonNull
    public static Pageable getPageable(int pageNumber, int pageSize) {
        return PageRequest.of(pageNumber - 1, pageSize);
    }

    /**
     * Executes a paginated search and returns a page of results.
     */
    public static <T> Page<T> getPage(int pageNumber, int pageSize, Function<Pageable, Page<T>> searchFunction) {
        Pageable pageable = getPageable(pageNumber, pageSize);

        Page<T> page = searchFunction.apply(pageable);

        // Fallback to the last page if the requested page is out of bounds
        if (!isValidPage(page)) {
            pageable = getPageable(Math.max(page.getTotalPages(), 1), pageSize);
            page = searchFunction.apply(pageable);
        }

        return page;
    }

    /**
     * Maps a page content into a paginated DTO result.
     */
    public static <T, R> PaginatedResult<R> getResult(Page<T> page, Function<T, R> mapFunction) {
        List<R> mappedContent = page.getContent().stream()
                .map(mapFunction)
                .toList();

        return new PaginatedResult<>(mappedContent, page.getTotalElements(), page.getTotalPages(),
                page.getSize(), page.getNumber() + 1);
    }

    /**
     * Validates if the page number is within the valid range.
     */
    public static <T> boolean isValidPage(Page<T> page) {
        int pageNumber = page.getNumber() + 1;
        int pageCount = page.getTotalPages();

        return pageNumber > 0 && pageNumber <= Math.max(pageCount, 1);
    }
}