package com.mms.mms_api.business.handlers;

import com.mms.mms_api.business.commands.UserCreateCommand;
import com.mms.mms_api.data.UserRepository;
import com.mms.mms_api.dto.UserDto;
import com.mms.mms_api.models.User;
import com.mms.mms_api.utils.mappers.UserMapper;

public class UserCreateHandler extends BaseHandler<UserCreateCommand, UserDto> {
    private UserRepository userRepository;
    
    public UserCreateHandler(
        UserCreateCommand request, 
        UserRepository userRepository, 
        UserMapper userMapper
    ) {
        super(request, userMapper);
        this.userRepository = userRepository;
    }

    public UserDto execute() {
        User user = userMapper.toEntity(request.getUserDto());
        
        User savedUser = userRepository.save(user);

        return userMapper.toDto(savedUser);
    }
}
