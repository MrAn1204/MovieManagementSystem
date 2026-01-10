package com.mms.mms_api.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.lang.NonNull;

public class SearchHelper {
    private SearchHelper() {
    }

    @NonNull
    public static Pageable generatePageable(@NonNull Direction sortDirection, @NonNull String sortBy, int pageNumber, int pageSize) {
        Sort sort = Sort.by(sortDirection, sortBy);

        return PageRequest.of(pageNumber - 1, pageSize, sort);
    }
}