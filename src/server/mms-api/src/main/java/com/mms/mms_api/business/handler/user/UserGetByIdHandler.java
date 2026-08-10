package com.mms.mms_api.business.handler.user;

import org.springframework.stereotype.Component;

import com.mms.mms_api.business.query.user.UserGetByIdQuery;
import com.mms.mms_api.data.UserRepository;
import com.mms.mms_api.dto.AuditDto;
import com.mms.mms_api.dto.user.UserDetailDto;
import com.mms.mms_api.exception.ResourceNotFoundException;
import com.mms.mms_api.model.User;
import com.mms.mms_api.util.CurrentUserHelper;
import com.mms.mms_api.util.mapper.UserMapper;

/**
 * Handles requests to retrieve user by id.
 */
@Component
public class UserGetByIdHandler extends UserBaseHandler<UserGetByIdQuery, UserDetailDto> {
    private final CurrentUserHelper currentUser;

    /**
     * Creates a UserGetByIdHandler.
     *
     * @param userMapper user mapper
     * @param userRepository user repository
     * @param currentUserHelper current user helper
     */
    public UserGetByIdHandler(UserMapper userMapper, UserRepository userRepository, CurrentUserHelper currentUserHelper) {
        super(userMapper, userRepository);
        this.currentUser = currentUserHelper;
    }

    /**
     * Retrieves a user by their identifier.
     *
     * @param request query containing the target user id
     * @return user detail DTO
     * @throws com.mms.mms_api.exception.ResourceNotFoundException when the user does not exist
     */
    @Override
    public UserDetailDto execute(UserGetByIdQuery request) {
        User user = userRepository.findById(request.getId()).orElseThrow(
                () -> new ResourceNotFoundException("user.notFound"));

        UserDetailDto dto = userMapper.toDetailDto(user);

        if (currentUser.isAdmin()) {
            dto.setAudit(new AuditDto(user));
        }

        return dto;
    }

}
