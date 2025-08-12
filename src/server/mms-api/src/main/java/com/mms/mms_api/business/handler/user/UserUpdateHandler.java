package com.mms.mms_api.business.handler.user;

import java.util.List;

import com.mms.mms_api.business.command.user.UserUpdateCommand;
import com.mms.mms_api.data.RoleRepository;
import com.mms.mms_api.data.UserRepository;
import com.mms.mms_api.dto.user.UserDto;
import com.mms.mms_api.exception.ErrorMessage;
import com.mms.mms_api.exception.ResourceNotFoundException;
import com.mms.mms_api.model.Role;
import com.mms.mms_api.model.User;
import com.mms.mms_api.util.mapper.UserMapper;
import com.mms.mms_api.util.validator.UserValidator;

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
        User user = userRepository.findById(request.getId()).orElseThrow(
                () -> new ResourceNotFoundException(ErrorMessage.USER_NOT_FOUND));

        UserValidator.validateUsername(request.getUsername());
        UserValidator.validateFullname(request.getFullname());
        UserValidator.validatePassword(request.getPassword());
        UserValidator.validateGender(request.getGender());
        UserValidator.validateDateOfBirth(request.getDateOfBirth());
        UserValidator.validatePhoneNumber(request.getPhoneNumber());
        
        List<String> roleNames = request.getRoles();
        List<Role> mappedRoles = roleRepository.findByNameIn(roleNames);
        UserValidator.validateRoles(roleNames, mappedRoles);

        userMapper.updateEntity(request, user);
        
        user.setRoles(mappedRoles);

        User updatedUser = userRepository.save(user);

        return userMapper.toDto(updatedUser);
    }
}
