package com.mms.mms_api.business.handler.user;

import org.springframework.stereotype.Component;

import com.mms.mms_api.business.query.user.UserGetByIdQuery;
import com.mms.mms_api.data.UserRepository;
import com.mms.mms_api.dto.user.UserDetailDto;
import com.mms.mms_api.exception.ResourceNotFoundException;
import com.mms.mms_api.model.User;
import com.mms.mms_api.util.mapper.UserMapper;

@Component
public class UserGetByIdHandler extends UserBaseHandler<UserGetByIdQuery, UserDetailDto> {
    public UserGetByIdHandler(UserMapper userMapper, UserRepository userRepository) {
        super(userMapper, userRepository);
    }

    @Override
    public UserDetailDto execute(UserGetByIdQuery request) {
        User user = userRepository.findById(request.getId()).orElseThrow(
                () -> new ResourceNotFoundException("user.notFound"));

        return userMapper.toDetailDto(user);
    }

}
