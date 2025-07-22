package com.mms.mms_api.business.handler.user;

import java.util.List;
import java.util.Optional;

import org.springframework.util.CollectionUtils;

import com.mms.mms_api.business.command.user.UserUpdateCommand;
import com.mms.mms_api.data.RoleRepository;
import com.mms.mms_api.data.UserRepository;
import com.mms.mms_api.dto.user.UserDto;
import com.mms.mms_api.model.Role;
import com.mms.mms_api.model.User;
import com.mms.mms_api.util.mapper.UserMapper;

public class UserUpdateHandler extends UserBaseHandler<UserUpdateCommand, UserDto> {
    private RoleRepository roleRepository;

    public UserUpdateHandler(
            UserUpdateCommand request, UserMapper userMapper, 
            UserRepository userRepository, RoleRepository roleRepository) {
        super(request, userMapper, userRepository);
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDto execute() {
        Optional<User> optionalUser = userRepository.findById(request.getId());

        if (!optionalUser.isPresent()) {
            return null;
        }

        User user = optionalUser.get();

        userMapper.updateEntity(request, user);

        if (!CollectionUtils.isEmpty(request.getRoles())) {
            List<Role> mappedRoles = roleRepository.findByNameIn(request.getRoles());

            user.setRoles(mappedRoles);
        }

        User updatedUser = userRepository.save(user);

        return userMapper.toDto(updatedUser);
    }

}
