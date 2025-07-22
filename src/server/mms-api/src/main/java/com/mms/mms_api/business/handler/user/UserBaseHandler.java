package com.mms.mms_api.business.handler.user;

import com.mms.mms_api.business.handler.BaseHandler;
import com.mms.mms_api.data.UserRepository;
import com.mms.mms_api.util.mapper.UserMapper;

public abstract class UserBaseHandler<I, O> extends BaseHandler<I, O>  {
    protected UserMapper userMapper;

    protected UserRepository userRepository;

    protected UserBaseHandler(I request, UserMapper userMapper, UserRepository userRepository) {
        super(request);
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }
}
