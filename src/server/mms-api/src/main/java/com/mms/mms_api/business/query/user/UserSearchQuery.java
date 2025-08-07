package com.mms.mms_api.business.query.user;

import com.mms.mms_api.business.query.BaseSearchQuery;
import com.mms.mms_api.business.query.SortDirection;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserSearchQuery extends BaseSearchQuery {
    private String role;

    public UserSearchQuery(int pageNumber, int pageSize, String keyword, String sortBy, SortDirection sortDirection,
            String role) {
        super(pageNumber, pageSize, keyword, sortBy, sortDirection);
        this.role = role;
    }

}
