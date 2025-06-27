package com.mms.mms_api.business.commands.user;

import java.util.UUID;

import com.mms.mms_api.business.commands.BaseGetByIdQuery;

public class UserGetByIdQuery extends BaseGetByIdQuery {
    public UserGetByIdQuery(UUID id) {
        super(id);
    }
}
