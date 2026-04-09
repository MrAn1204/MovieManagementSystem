package com.mms.mms_api.business.handler.user;

import org.springframework.stereotype.Component;

import java.util.List;

import com.mms.mms_api.business.query.user.UserGetAllQuery;
import com.mms.mms_api.data.UserRepository;
import com.mms.mms_api.dto.user.UserDto;
import com.mms.mms_api.model.User;
import com.mms.mms_api.util.mapper.UserMapper;

@Component
public class UserGetAllHandler extends UserBaseHandler<UserGetAllQuery, List<UserDto>> {
    public UserGetAllHandler(UserMapper userMapper, UserRepository userRepository) {
        super(userMapper, userRepository);
        this.userRepository = userRepository;
    }

    @Override
    public List<UserDto> execute(UserGetAllQuery request) {
        List<User> users = userRepository.findAll();

        return users.stream().map(u -> userMapper.toDto(u)).toList();
    }

}
