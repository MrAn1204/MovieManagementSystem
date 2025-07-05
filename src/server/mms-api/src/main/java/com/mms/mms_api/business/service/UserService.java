package com.mms.mms_api.business.service;

import java.util.Collection;

import org.springframework.stereotype.Service;

import com.mms.mms_api.business.command.user.UserCreateCommand;
import com.mms.mms_api.business.command.user.UserDeleteCommand;
import com.mms.mms_api.business.command.user.UserUpdateCommand;
import com.mms.mms_api.business.handler.UserCreateHandler;
import com.mms.mms_api.business.handler.UserDeleteHandler;
import com.mms.mms_api.business.handler.UserGetAllHandler;
import com.mms.mms_api.business.handler.UserGetByIdHandler;
import com.mms.mms_api.business.handler.UserUpdateHandler;
import com.mms.mms_api.business.query.user.UserGetAllQuery;
import com.mms.mms_api.business.query.user.UserGetByIdQuery;
import com.mms.mms_api.data.RoleRepository;
import com.mms.mms_api.data.UserRepository;
import com.mms.mms_api.dto.user.UserDto;
import com.mms.mms_api.util.mapper.UserMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;

    private RoleRepository roleRepository;

    private UserMapper userMapper;

    public UserDto handle(UserCreateCommand request) {
        UserCreateHandler handler = new UserCreateHandler(request, userMapper, userRepository, roleRepository);

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

    public UserDto handle(UserUpdateCommand request) {
        UserUpdateHandler handler = new UserUpdateHandler(request, userMapper, userRepository, roleRepository);

        return handler.execute();
    }

    public Boolean handle(UserDeleteCommand request) {
        UserDeleteHandler handler = new UserDeleteHandler(request, userRepository);

        return handler.execute();
    }
}
