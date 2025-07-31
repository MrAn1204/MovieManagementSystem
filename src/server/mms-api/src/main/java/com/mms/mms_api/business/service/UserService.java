package com.mms.mms_api.business.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mms.mms_api.business.command.user.UserCreateCommand;
import com.mms.mms_api.business.command.user.UserDeleteCommand;
import com.mms.mms_api.business.command.user.UserUpdateCommand;
import com.mms.mms_api.business.handler.user.UserCreateHandler;
import com.mms.mms_api.business.handler.user.UserDeleteHandler;
import com.mms.mms_api.business.handler.user.UserGetAllHandler;
import com.mms.mms_api.business.handler.user.UserGetByIdHandler;
import com.mms.mms_api.business.handler.user.UserSearchHandler;
import com.mms.mms_api.business.handler.user.UserUpdateHandler;
import com.mms.mms_api.business.query.user.UserGetAllQuery;
import com.mms.mms_api.business.query.user.UserGetByIdQuery;
import com.mms.mms_api.business.query.user.UserSearchQuery;
import com.mms.mms_api.common.PaginatedResult;
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

    public List<UserDto> handle(UserGetAllQuery request) {
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

    public void handle(UserDeleteCommand request) {
        UserDeleteHandler handler = new UserDeleteHandler(request, userRepository);

        handler.execute();
    }

    public PaginatedResult<UserDto> handle(UserSearchQuery request) {
        UserSearchHandler handler = new UserSearchHandler(request, userMapper, userRepository);
        
        return handler.execute();
    }
}
