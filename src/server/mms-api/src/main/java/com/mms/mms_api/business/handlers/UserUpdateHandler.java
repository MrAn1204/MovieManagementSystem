package com.mms.mms_api.business.handlers;

import java.util.Optional;

import com.mms.mms_api.business.commands.user.UserUpdateCommand;
import com.mms.mms_api.data.UserRepository;
import com.mms.mms_api.dto.user.UserDto;
import com.mms.mms_api.models.User;
import com.mms.mms_api.utils.mappers.UserMapper;

public class UserUpdateHandler extends BaseHandler<UserUpdateCommand, UserDto> {
    private UserRepository userRepository;

    public UserUpdateHandler(UserUpdateCommand request, UserMapper userMapper, UserRepository userRepository) {
        super(request, userMapper);
        this.userRepository = userRepository;
    }

    @Override
    public UserDto execute() {
        Optional<User> optionalUser = userRepository.findById(request.getId());

        if (!optionalUser.isPresent()) {
            return null;
        }

        User user = optionalUser.get();

        userMapper.updateEntity(request, user);

        return userMapper.toDto(userRepository.save(user));
    }

}
