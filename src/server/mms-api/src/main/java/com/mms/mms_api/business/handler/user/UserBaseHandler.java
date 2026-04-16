package com.mms.mms_api.business.handler.user;

import com.mms.mms_api.business.handler.BaseHandler;
import com.mms.mms_api.data.UserRepository;
import com.mms.mms_api.util.mapper.UserMapper;

/**
 * Base handler for user-related requests.
 */
public abstract class UserBaseHandler<I, O> extends BaseHandler<I, O>  {
    protected UserMapper userMapper;

    protected UserRepository userRepository;

    /**
     * Creates a user base handler.
     *
     * @param userMapper user mapper
     * @param userRepository user repository
     */
    protected UserBaseHandler(UserMapper userMapper, UserRepository userRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }
}
