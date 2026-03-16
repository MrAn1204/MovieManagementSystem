package com.mms.mms_api.business.handler.user;

import com.mms.mms_api.business.query.user.UserGetByIdQuery;
import com.mms.mms_api.data.UserRepository;
import com.mms.mms_api.dto.user.UserDetailDto;
import com.mms.mms_api.exception.ResourceNotFoundException;
import com.mms.mms_api.model.User;
import com.mms.mms_api.util.mapper.UserMapper;

public class UserGetByIdHandler extends UserBaseHandler<UserGetByIdQuery, UserDetailDto> {
    public UserGetByIdHandler(UserGetByIdQuery request, UserMapper userMapper, UserRepository userRepository) {
        super(request, userMapper, userRepository);
    }

    @Override
    public UserDetailDto execute() {
        User user = userRepository.findById(request.getId()).orElseThrow(
                () -> new ResourceNotFoundException("user.notFound"));

        return userMapper.toDetailDto(user);
    }

}
