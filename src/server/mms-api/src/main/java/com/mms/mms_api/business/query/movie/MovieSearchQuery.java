package com.mms.mms_api.business.query.movie;

import java.util.List;

import com.mms.mms_api.business.query.BaseSearchQuery;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MovieSearchQuery extends BaseSearchQuery {
    private List<String> genres;
    
    private String language;

}
