package com.mms.mms_api.business.query.user;

import java.util.UUID;

import com.mms.mms_api.business.query.BaseGetByIdQuery;

public class UserGetByIdQuery extends BaseGetByIdQuery {
    public UserGetByIdQuery(UUID id) {
        super(id);
    }
}
