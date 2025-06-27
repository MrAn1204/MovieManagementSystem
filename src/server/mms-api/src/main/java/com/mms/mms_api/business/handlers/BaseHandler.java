package com.mms.mms_api.business.handlers;

import com.mms.mms_api.utils.mappers.UserMapper;

public abstract class BaseHandler<I, O> {
    protected I command;
    
    protected UserMapper userMapper;

    protected BaseHandler(
        I command,
        UserMapper userMapper
    ) {
        this.command = command;
        this.userMapper = userMapper;
    }
    
    public abstract O execute();
}
