package com.mms.mms_api.business.handlers;

import java.util.Collection;

import com.mms.mms_api.business.query.user.UserGetAllQuery;
import com.mms.mms_api.data.UserRepository;
import com.mms.mms_api.dto.user.UserDto;
import com.mms.mms_api.models.User;
import com.mms.mms_api.utils.mappers.UserMapper;

public class UserGetAllHandler extends BaseHandler<UserGetAllQuery, Collection<UserDto>> {
    private UserRepository userRepository;

    public UserGetAllHandler(UserGetAllQuery request, UserMapper userMapper, UserRepository userRepository) {
        super(request, userMapper);
        this.userRepository = userRepository;
    }

    @Override
    public Collection<UserDto> execute() {
        Collection<User> users = userRepository.findAll();

        return users.stream().map(u -> userMapper.toDto(u)).toList();
    }

}
