package com.mms.mms_api.business.handlers;

import java.util.Optional;

import com.mms.mms_api.business.commands.user.UserGetByIdQuery;
import com.mms.mms_api.data.UserRepository;
import com.mms.mms_api.dto.UserDto;
import com.mms.mms_api.models.User;
import com.mms.mms_api.utils.mappers.UserMapper;

public class UserGetByIdHandler extends BaseHandler<UserGetByIdQuery, UserDto> {
    private UserRepository userRepository;

    public UserGetByIdHandler(UserGetByIdQuery request, UserMapper userMapper, UserRepository userRepository) {
        super(request, userMapper);
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
