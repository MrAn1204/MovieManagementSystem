package com.mms.mms_api.business.query.movie;

import java.util.List;

import com.mms.mms_api.business.query.BaseSearchQuery;
import com.mms.mms_api.business.query.SortDirection;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MovieSearchQuery extends BaseSearchQuery {
    private List<String> genres;
    
    private String language;

    public MovieSearchQuery(int pageNumber, int pageSize, String keyword, String sortBy, SortDirection sortDirection,
            List<String> genres, String language) {
        super(pageNumber, pageSize, keyword, sortBy, sortDirection);
        this.language = language;
        this.genres = genres;
    }

}
