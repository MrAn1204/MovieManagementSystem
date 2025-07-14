package com.mms.mms_api.business.query.user;

import com.mms.mms_api.business.query.BaseSearchQuery;
import com.mms.mms_api.business.query.SortDirection;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserSearchQuery extends BaseSearchQuery {

    public UserSearchQuery(int pageNumber, int pageSize, String keyword, String sortBy, SortDirection sortDirection) {
        super(pageNumber, pageSize, keyword, sortBy, sortDirection);
    }

}
