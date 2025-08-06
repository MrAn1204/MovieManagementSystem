package com.mms.mms_api.business.query.movie;

import com.mms.mms_api.business.query.BaseSearchQuery;
import com.mms.mms_api.business.query.SortDirection;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MovieSearchQuery extends BaseSearchQuery {

    public MovieSearchQuery(int pageNumber, int pageSize, String keyword, String sortBy, SortDirection sortDirection) {
        super(pageNumber, pageSize, keyword, sortBy, sortDirection);
    }

}
