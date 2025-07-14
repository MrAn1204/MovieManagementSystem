package com.mms.mms_api.business.query;

import java.io.Serializable;

import lombok.Data;

@Data
public abstract class BaseSearchQuery implements Serializable {
    private int pageNumber = 1;
    
    private int pageSize = 10;
    
    private String keyword;
    
    private String sortBy = "id";
    
    private SortDirection sortDirection = SortDirection.ASC;

    protected BaseSearchQuery(Integer pageNumber, Integer pageSize, String keyword, String sortBy, SortDirection sortDirection) {
        this.pageNumber = pageNumber > 0 ? pageNumber : 1;
        this.pageSize = pageSize > 0 ? pageSize : 10;
        this.keyword = keyword;
        this.sortBy = sortBy != null ? sortBy : "id";
        this.sortDirection = sortDirection != null ? sortDirection : SortDirection.ASC;
    }
}