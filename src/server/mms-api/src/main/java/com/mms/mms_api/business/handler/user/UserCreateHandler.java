package com.mms.mms_api.business.handler.user;

import org.springframework.stereotype.Component;

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

/**
 * Handles user creation commands.
 */
@Component
public class UserCreateHandler extends UserBaseHandler<UserCreateCommand, UserDetailDto> {
    private RoleRepository roleRepository;

    private PasswordEncoder passwordEncoder;

    /**
     * Creates a UserCreateHandler.
     *
     * @param userMapper user mapper
     * @param userRepository user repository
     * @param roleRepository role repository
     * @param passwordEncoder password encoder
     */
    public UserCreateHandler(UserMapper userMapper,
            UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        super(userMapper, userRepository);
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Creates a new user with resolved roles and an encoded password.
     *
     * @param request user create command
     * @return created user detail DTO
     */
    public UserDetailDto execute(UserCreateCommand request) {
        List<UUID> roleIds = request.getRoleIds();
        List<Role> mappedRoles = roleRepository.findAllById(roleIds);

        User user = userMapper.toEntity(request);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(mappedRoles);

        User savedUser = userRepository.save(user);

        return userMapper.toDetailDto(savedUser);
    }
}
