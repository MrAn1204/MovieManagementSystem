package com.mms.mms_api.business.query;

import java.io.Serializable;

import org.springframework.data.domain.Sort.Direction;
import org.springframework.lang.NonNull;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Base type for search queries with pagination and sorting.
 */
@Data
public abstract class BaseSearchQuery implements BaseQuery, Serializable {
    @Min(value = 1, message = "{search.pageNumber.min}")
    private int pageNumber = 1;

    @Min(value = 10, message = "{search.pageSize.min}")
    private int pageSize = 10;

    private String keyword;

    @NotNull(message = "{search.sortBy.required}")
    @NonNull
    private String sortBy = "createdAt";

    @NotNull(message = "{search.sortDirection.required}")
    @NonNull
    private Direction sortDirection = Direction.ASC;
}