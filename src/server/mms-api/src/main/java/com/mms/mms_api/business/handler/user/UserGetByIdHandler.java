package com.mms.mms_api.business.handler.user;

import java.util.Optional;

import com.mms.mms_api.business.query.user.UserGetByIdQuery;
import com.mms.mms_api.data.UserRepository;
import com.mms.mms_api.dto.user.UserDto;
import com.mms.mms_api.model.User;
import com.mms.mms_api.util.mapper.UserMapper;

public class UserGetByIdHandler extends UserBaseHandler<UserGetByIdQuery, UserDto> {
    public UserGetByIdHandler(UserGetByIdQuery request, UserMapper userMapper, UserRepository userRepository) {
        super(request, userMapper, userRepository);
        this.userRepository = userRepository;
    }

    @Override
    public UserDto execute() {
        Optional<User> userOptional = userRepository.findById(request.getId());

        if (!userOptional.isPresent()) {
            return null;
        }
        
        return userMapper.toDto(userOptional.get());
    }

}
