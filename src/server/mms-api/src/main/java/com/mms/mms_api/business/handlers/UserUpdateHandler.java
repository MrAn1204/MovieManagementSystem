package com.mms.mms_api.business.handlers;

import java.util.Optional;

import com.mms.mms_api.business.commands.user.UserUpdateCommand;
import com.mms.mms_api.data.UserRepository;
import com.mms.mms_api.dto.UserDto;
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
        UserDto userDto = request.getUserDto();

        Optional<User> optionalUser = userRepository.findById(userDto.getId());

        if (!optionalUser.isPresent()) {
            return null;
        }

        User user = optionalUser.get();

        userMapper.updateFromDto(userDto, user);

        return userMapper.toDto(userRepository.save(user));
    }

}
