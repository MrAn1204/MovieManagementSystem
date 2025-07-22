package com.mms.mms_api.business.handler.user;

import java.util.List;

import com.mms.mms_api.business.query.user.UserGetAllQuery;
import com.mms.mms_api.data.UserRepository;
import com.mms.mms_api.dto.user.UserDto;
import com.mms.mms_api.model.User;
import com.mms.mms_api.util.mapper.UserMapper;

public class UserGetAllHandler extends UserBaseHandler<UserGetAllQuery, List<UserDto>> {
    public UserGetAllHandler(UserGetAllQuery request, UserMapper userMapper, UserRepository userRepository) {
        super(request, userMapper, userRepository);
        this.userRepository = userRepository;
    }

    @Override
    public List<UserDto> execute() {
        List<User> users = userRepository.findAll();

        return users.stream().map(u -> userMapper.toDto(u)).toList();
    }

}
