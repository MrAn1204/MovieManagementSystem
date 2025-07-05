package com.mms.mms_api.business.handler;

import com.mms.mms_api.util.mapper.UserMapper;

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
