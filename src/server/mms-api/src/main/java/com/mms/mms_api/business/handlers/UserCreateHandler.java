package com.mms.mms_api.business.handlers;

import java.util.Collection;

import org.springframework.util.CollectionUtils;

import com.mms.mms_api.business.commands.user.UserCreateCommand;
import com.mms.mms_api.data.UserRepository;
import com.mms.mms_api.dto.user.UserDto;
import com.mms.mms_api.models.User;
import com.mms.mms_api.utils.mappers.UserMapper;

public class UserCreateHandler extends BaseHandler<UserCreateCommand, UserDto> {
    private UserRepository userRepository;

    public UserCreateHandler(
        UserCreateCommand request,
        UserMapper userMapper,
        UserRepository userRepository 
    ) {
        super(request, userMapper);
        this.userRepository = userRepository;
    }

    public UserDto execute() {
        Collection<String> roles = request.getRoles();

        if (CollectionUtils.isEmpty(roles)) {
            return null;
        }

        // TODO: Map roles and assign roles to new user

        User user = userMapper.toEntity(request);
        
        User savedUser = userRepository.save(user);

        return userMapper.toDto(savedUser);
    }
}
