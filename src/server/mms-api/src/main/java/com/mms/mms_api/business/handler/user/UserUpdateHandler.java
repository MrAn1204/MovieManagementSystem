package com.mms.mms_api.business.handler.user;

import java.util.List;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.mms.mms_api.business.command.user.UserUpdateCommand;
import com.mms.mms_api.data.RoleRepository;
import com.mms.mms_api.data.UserRepository;
import com.mms.mms_api.dto.user.UserDto;
import com.mms.mms_api.exception.InvalidInputException;
import com.mms.mms_api.exception.ResourceNotFoundException;
import com.mms.mms_api.model.Role;
import com.mms.mms_api.model.User;
import com.mms.mms_api.util.mapper.UserMapper;
import com.mms.mms_api.util.validator.UserValidator;

public class UserUpdateHandler extends UserBaseHandler<UserUpdateCommand, UserDto> {
    private RoleRepository roleRepository;

    private PasswordEncoder passwordEncoder;

    public UserUpdateHandler(
            UserUpdateCommand request, UserMapper userMapper,
            UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        super(request, userMapper, userRepository);
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDto execute() {
        User user = userRepository.findById(request.getId()).orElseThrow(
                () -> new ResourceNotFoundException("user.notFound"));

        List<UUID> roleIds = request.getRoleIds();
        List<Role> mappedRoles = (roleIds != null)
                ? roleRepository.findAllById(roleIds)
                : null;
        UserValidator.validateRoles(roleIds, mappedRoles);

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new InvalidInputException("user.username.unique");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new InvalidInputException("user.email.unique");
        }

        if (userRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new InvalidInputException("user.phone.unique");
        }

        userMapper.updateEntity(request, user);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user.setRoles(mappedRoles);

        User updatedUser = userRepository.save(user);

        return userMapper.toDto(updatedUser);
    }
}
