package com.mms.mms_api.business.handler.user;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.mms.mms_api.business.command.user.UserUpdateCommand;
import com.mms.mms_api.data.RoleRepository;
import com.mms.mms_api.data.UserRepository;
import com.mms.mms_api.dto.user.UserDetailDto;
import com.mms.mms_api.exception.ResourceNotFoundException;
import com.mms.mms_api.model.Role;
import com.mms.mms_api.model.User;
import com.mms.mms_api.util.mapper.UserMapper;

@Component
public class UserUpdateHandler extends UserBaseHandler<UserUpdateCommand, UserDetailDto> {
    private RoleRepository roleRepository;

    private PasswordEncoder passwordEncoder;

    public UserUpdateHandler(UserMapper userMapper,
            UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        super(userMapper, userRepository);
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetailDto execute(UserUpdateCommand request) {
        User user = userRepository.findById(request.getId()).orElseThrow(
                () -> new ResourceNotFoundException("user.notFound"));

        List<UUID> roleIds = request.getRoleIds();
        List<Role> mappedRoles = roleRepository.findAllById(roleIds);

        userMapper.updateEntity(request, user);

        String password = request.getPassword();
        if (password != null && !password.isBlank()) {
            user.setPassword(passwordEncoder.encode(password));
        }

        user.setRoles(mappedRoles);

        User updatedUser = userRepository.save(user);

        return userMapper.toDetailDto(updatedUser);
    }
}
