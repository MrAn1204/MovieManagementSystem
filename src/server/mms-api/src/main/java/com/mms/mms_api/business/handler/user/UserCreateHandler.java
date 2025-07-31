package com.mms.mms_api.business.handler.user;

import java.util.List;

import org.springframework.util.StringUtils;
import org.springframework.util.CollectionUtils;

import com.mms.mms_api.business.command.user.UserCreateCommand;
import com.mms.mms_api.data.RoleRepository;
import com.mms.mms_api.data.UserRepository;
import com.mms.mms_api.dto.user.UserDto;
import com.mms.mms_api.exception.ErrorMessage;
import com.mms.mms_api.exception.InvalidInputException;
import com.mms.mms_api.model.Role;
import com.mms.mms_api.model.User;
import com.mms.mms_api.util.mapper.UserMapper;

public class UserCreateHandler extends UserBaseHandler<UserCreateCommand, UserDto> {
    private RoleRepository roleRepository;

    public UserCreateHandler(
        UserCreateCommand request,
        UserMapper userMapper,
        UserRepository userRepository,
        RoleRepository roleRepository
    ) {
        super(request, userMapper, userRepository);
        this.roleRepository = roleRepository;
    }

    public UserDto execute() {
        validateRequest();

        List<String> roles = request.getRoles();
        List<Role> mappedRoles = roleRepository.findByNameIn(roles);

        if (mappedRoles.size() != roles.size()) {
            throw new InvalidInputException(ErrorMessage.INVALID_ROLES.getValue());
        }

        User user = userMapper.toEntity(request);

        user.setRoles(mappedRoles);
        
        User savedUser = userRepository.save(user);

        return userMapper.toDto(savedUser);
    }

    private void validateRequest() {
        if (!StringUtils.hasText(request.getUsername())) {
            throw new InvalidInputException(ErrorMessage.BLANK_USERNAME.getValue());
        }
        if (!StringUtils.hasText(request.getFullname())) {
            throw new InvalidInputException(ErrorMessage.BLANK_FULLNAME.getValue());
        }
        if (!StringUtils.hasText(request.getPassword())) {
            throw new InvalidInputException(ErrorMessage.BLANK_PASSWORD.getValue());
        }
        if (request.getGender() == null) {
            throw new InvalidInputException(ErrorMessage.GENDER_REQUIRED.getValue());
        }
        if (request.getDateOfBirth() == null) {
            throw new InvalidInputException(ErrorMessage.DOB_REQUIRED.getValue());
        }
        if (!StringUtils.hasText(request.getPhoneNumber())) {
            throw new InvalidInputException(ErrorMessage.BLANK_PHONE_NUMBER.getValue());
        }
        if (CollectionUtils.isEmpty(request.getRoles())) {
            throw new InvalidInputException(ErrorMessage.ROLES_REQUIRED.getValue());
        }
    }
}
