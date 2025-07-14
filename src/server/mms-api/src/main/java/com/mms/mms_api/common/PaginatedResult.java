package com.mms.mms_api.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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