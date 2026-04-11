package com.mms.mms_api.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Generic pagination payload for list responses.
 *
 * @param <T> type of item in the paginated collection
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginatedResult<T> {

    private List<T> items;
    private long itemCount;
    private int pageCount;
    private int pageSize;
    private int pageNumber;
}