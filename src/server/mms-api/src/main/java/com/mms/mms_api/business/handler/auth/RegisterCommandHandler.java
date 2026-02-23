package com.mms.mms_api.business.handler.auth;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.mms.mms_api.business.command.auth.RegisterCommand;
import com.mms.mms_api.data.RoleRepository;
import com.mms.mms_api.data.UserRepository;
import com.mms.mms_api.dto.user.UserDto;
import com.mms.mms_api.model.Role;
import com.mms.mms_api.model.User;
import com.mms.mms_api.util.mapper.UserMapper;

public class RegisterCommandHandler extends AuthBaseHandler<RegisterCommand, UserDto> {
    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    public RegisterCommandHandler(RegisterCommand request, UserRepository userRepository, RoleRepository roleRepository,
            UserMapper userMapper, PasswordEncoder passwordEncoder) {
        super(request, null, null);
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDto execute() {
        Role userRole = roleRepository.findByName("USER");

        User user = userMapper.toEntity(request);

        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(List.of(userRole));

        User savedUser = userRepository.save(user);

        return userMapper.toDto(savedUser);
    }
}
