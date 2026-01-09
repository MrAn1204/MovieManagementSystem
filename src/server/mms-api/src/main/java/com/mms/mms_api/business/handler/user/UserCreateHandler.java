package com.mms.mms_api.business.handler.user;

import java.util.List;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;

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

    private PasswordEncoder passwordEncoder;

    public UserCreateHandler(
            UserCreateCommand request, UserMapper userMapper,
            UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        super(request, userMapper, userRepository);
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDto execute() {
        List<UUID> roleIds = request.getRoles();
        List<Role> mappedRoles = (roleIds != null)
                ? roleRepository.findAllById(roleIds)
                : null;
        UserValidator.validateRoles(roleIds, mappedRoles);

        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user.setRoles(mappedRoles);

        User savedUser = userRepository.save(user);

        return userMapper.toDto(savedUser);
    }
}
