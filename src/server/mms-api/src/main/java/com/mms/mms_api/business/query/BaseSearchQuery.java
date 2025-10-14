package com.mms.mms_api.business.query;

import java.io.Serializable;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public abstract class BaseSearchQuery implements BaseQuery, Serializable {
    @Min(value = 1, message = "{search.pageNumber.min}")
    private int pageNumber = 1;

    @Min(value = 10, message = "{search.pageSize.min}")
    private int pageSize = 10;

    private String keyword;

    @NotNull(message = "{search.sortBy.required}")
    private String sortBy = "id";

    @NotNull(message = "{search.sortDirection.required}")
    private SortDirection sortDirection = SortDirection.ASC;
}