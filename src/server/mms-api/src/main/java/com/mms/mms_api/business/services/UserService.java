package com.mms.mms_api.business.services;

import org.springframework.stereotype.Service;

import com.mms.mms_api.business.commands.UserCreateCommand;
import com.mms.mms_api.business.handlers.UserCreateHandler;
import com.mms.mms_api.data.UserRepository;
import com.mms.mms_api.dto.UserDto;
import com.mms.mms_api.utils.mappers.UserMapper;

@Service
public class UserService {
    private UserRepository userRepository;

    private UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }
    
    public UserDto handle(UserCreateCommand command) {
        UserCreateHandler handler = new UserCreateHandler(command, userRepository, userMapper);

        return handler.execute();
    }
}
