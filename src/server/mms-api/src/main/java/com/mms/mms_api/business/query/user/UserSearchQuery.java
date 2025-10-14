package com.mms.mms_api.business.query.user;

import com.mms.mms_api.business.query.BaseSearchQuery;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserSearchQuery extends BaseSearchQuery {
    private String role;

}
