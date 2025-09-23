package com.mms.mms_api.business.handler.user;

import java.util.List;

import com.mms.mms_api.business.command.user.UserCreateCommand;
import com.mms.mms_api.data.RoleRepository;
import com.mms.mms_api.data.UserRepository;
import com.mms.mms_api.dto.user.UserDto;
import com.mms.mms_api.model.Role;
import com.mms.mms_api.model.User;
import com.mms.mms_api.util.mapper.UserMapper;
import com.mms.mms_api.util.validator.UserValidator;

public class UserCreateHandler extends UserBaseHandler<UserCreateCommand, UserDto> {
    private RoleRepository roleRepository;

    public UserCreateHandler(
            UserCreateCommand request,
            UserMapper userMapper,
            UserRepository userRepository,
            RoleRepository roleRepository) {
        super(request, userMapper, userRepository);
        this.roleRepository = roleRepository;
    }

    public UserDto execute() {
        List<String> roleNames = request.getRoles();
        List<Role> mappedRoles = roleRepository.findByNameIn(roleNames);
        UserValidator.validateRoles(roleNames, mappedRoles);

        User user = userMapper.toEntity(request);

        user.setRoles(mappedRoles);

        User savedUser = userRepository.save(user);

        return userMapper.toDto(savedUser);
    }
}
