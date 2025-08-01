package com.mms.mms_api.business.handler.user;

import java.util.List;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.mms.mms_api.business.command.user.UserUpdateCommand;
import com.mms.mms_api.data.RoleRepository;
import com.mms.mms_api.data.UserRepository;
import com.mms.mms_api.dto.user.UserDto;
import com.mms.mms_api.exception.ErrorMessage;
import com.mms.mms_api.exception.InvalidInputException;
import com.mms.mms_api.exception.ResourceNotFoundException;
import com.mms.mms_api.model.Role;
import com.mms.mms_api.model.User;
import com.mms.mms_api.util.mapper.UserMapper;

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
        validateRequest();

        User user = userRepository.findById(request.getId())
                .orElseThrow(ResourceNotFoundException::new);

        userMapper.updateEntity(request, user);

        if (!CollectionUtils.isEmpty(request.getRoles())) {
            List<Role> mappedRoles = roleRepository.findByNameIn(request.getRoles());

            if (mappedRoles.size() != request.getRoles().size()) {
                throw new InvalidInputException(ErrorMessage.ROLES_INVALID.getValue());
            }

            user.setRoles(mappedRoles);
        }

        User updatedUser = userRepository.save(user);

        return userMapper.toDto(updatedUser);
    }

    private void validateRequest() {
        if (!StringUtils.hasText(request.getUsername())) {
            throw new InvalidInputException(ErrorMessage.USERNAME_REQUIRED.getValue());
        }
        if (!StringUtils.hasText(request.getFullname())) {
            throw new InvalidInputException(ErrorMessage.FULLNAME_REQUIRED.getValue());
        }
        if (!StringUtils.hasText(request.getPassword())) {
            throw new InvalidInputException(ErrorMessage.PASSWORD_REQUIRED.getValue());
        }
        if (request.getGender() == null) {
            throw new InvalidInputException(ErrorMessage.GENDER_REQUIRED.getValue());
        }
        if (request.getDateOfBirth() == null) {
            throw new InvalidInputException(ErrorMessage.DOB_REQUIRED.getValue());
        }
        if (!StringUtils.hasText(request.getPhoneNumber())) {
            throw new InvalidInputException(ErrorMessage.PHONE_REQUIRED.getValue());
        }
        if (CollectionUtils.isEmpty(request.getRoles())) {
            throw new InvalidInputException(ErrorMessage.ROLES_REQUIRED.getValue());
        }
    }

}
