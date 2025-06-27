package com.mms.mms_api.business.handlers;

import com.mms.mms_api.utils.mappers.UserMapper;

public abstract class BaseHandler<I, O> {
    protected I request;
    
    protected UserMapper userMapper;

    protected BaseHandler(
        I request,
        UserMapper userMapper
    ) {
        this.request = request;
        this.userMapper = userMapper;
    }
    
    public abstract O execute();
}
