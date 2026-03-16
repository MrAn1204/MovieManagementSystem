package com.mms.mms_api.business.handler.user;

import java.util.List;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.mms.mms_api.business.command.user.UserCreateCommand;
import com.mms.mms_api.data.RoleRepository;
import com.mms.mms_api.data.UserRepository;
import com.mms.mms_api.dto.user.UserDetailDto;
import com.mms.mms_api.model.Role;
import com.mms.mms_api.model.User;
import com.mms.mms_api.util.mapper.UserMapper;

public class UserCreateHandler extends UserBaseHandler<UserCreateCommand, UserDetailDto> {
    private RoleRepository roleRepository;

    private PasswordEncoder passwordEncoder;

    public UserCreateHandler(
            UserCreateCommand request, UserMapper userMapper,
            UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        super(request, userMapper, userRepository);
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDetailDto execute() {
        List<UUID> roleIds = request.getRoleIds();
        List<Role> mappedRoles = roleRepository.findAllById(roleIds);

        User user = userMapper.toEntity(request);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(mappedRoles);

        User savedUser = userRepository.save(user);

        return userMapper.toDetailDto(savedUser);
    }
}
