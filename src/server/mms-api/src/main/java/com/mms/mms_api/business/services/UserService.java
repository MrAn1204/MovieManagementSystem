package com.mms.mms_api.business.services;

import java.util.Collection;

import org.springframework.stereotype.Service;

import com.mms.mms_api.business.commands.user.UserCreateCommand;
import com.mms.mms_api.business.commands.user.UserGetAllQuery;
import com.mms.mms_api.business.commands.user.UserGetByIdQuery;
import com.mms.mms_api.business.handlers.UserCreateHandler;
import com.mms.mms_api.business.handlers.UserGetAllHandler;
import com.mms.mms_api.business.handlers.UserGetByIdHandler;
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
    
    public UserDto handle(UserCreateCommand request) {
        UserCreateHandler handler = new UserCreateHandler(request, userMapper, userRepository);

        return handler.execute();
    }

    public Collection<UserDto> handle(UserGetAllQuery request) {
        UserGetAllHandler handler = new UserGetAllHandler(request, userMapper, userRepository);

        return handler.execute();
    }

    public UserDto handle(UserGetByIdQuery request) {
        UserGetByIdHandler handler = new UserGetByIdHandler(request, userMapper, userRepository);

        return handler.execute();
    }
}
