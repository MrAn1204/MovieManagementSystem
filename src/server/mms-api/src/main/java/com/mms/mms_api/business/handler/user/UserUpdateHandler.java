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

/**
 * Handles user update commands.
 */
@Component
public class UserUpdateHandler extends UserBaseHandler<UserUpdateCommand, UserDetailDto> {
    private RoleRepository roleRepository;

    private PasswordEncoder passwordEncoder;

    /**
     * Creates a UserUpdateHandler.
     *
     * @param userMapper user mapper
     * @param userRepository user repository
     * @param roleRepository role repository
     * @param passwordEncoder password encoder
     */
    public UserUpdateHandler(UserMapper userMapper,
            UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        super(userMapper, userRepository);
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Updates an existing user, re-encodes the password when changed, and reassigns roles.
     *
     * @param request user update command
     * @return updated user detail DTO
     * @throws com.mms.mms_api.exception.ResourceNotFoundException when the user does not exist
     */
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
