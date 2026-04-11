package com.mms.mms_api.business.handler.auth;

import org.springframework.stereotype.Component;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.mms.mms_api.business.command.auth.RegisterCommand;
import com.mms.mms_api.data.RoleRepository;
import com.mms.mms_api.data.UserRepository;
import com.mms.mms_api.dto.user.UserDto;
import com.mms.mms_api.model.Role;
import com.mms.mms_api.model.User;
import com.mms.mms_api.util.mapper.UserMapper;

/**
 * Handles registration commands.
 */
@Component
public class RegisterCommandHandler extends AuthBaseHandler<RegisterCommand, UserDto> {
    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    /**
     * Creates a RegisterCommandHandler.
     *
     * @param userRepository user repository
     * @param roleRepository role repository
     * @param userMapper user mapper
     * @param passwordEncoder password encoder
     */
    public RegisterCommandHandler(UserRepository userRepository, RoleRepository roleRepository,
            UserMapper userMapper, PasswordEncoder passwordEncoder) {
        super(null, null);
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Registers a new user with the default USER role.
     *
     * @param request registration command containing user details
     * @return created user DTO
     */
    @Override
    public UserDto execute(RegisterCommand request) {
        Role userRole = roleRepository.findByName("USER");

        User user = userMapper.toEntity(request);

        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(List.of(userRole));

        User savedUser = userRepository.save(user);

        return userMapper.toDto(savedUser);
    }
}
