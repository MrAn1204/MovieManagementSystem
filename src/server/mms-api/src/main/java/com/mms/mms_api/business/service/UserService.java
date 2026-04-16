package com.mms.mms_api.business.service;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.mms.mms_api.business.command.user.UserCreateCommand;
import com.mms.mms_api.business.command.user.UserDeleteCommand;
import com.mms.mms_api.business.command.user.UserUpdateCommand;
import com.mms.mms_api.business.query.user.UserGetAllQuery;
import com.mms.mms_api.business.query.user.UserGetByIdQuery;
import com.mms.mms_api.business.query.user.UserSearchQuery;
import com.mms.mms_api.common.PaginatedResult;
import com.mms.mms_api.dto.user.UserDetailDto;
import com.mms.mms_api.dto.user.UserDto;
import com.mms.mms_api.mediator.RequestMediator;
import com.mms.mms_api.util.validator.UserValidator;

import lombok.AllArgsConstructor;

/**
 * Provides CRUD and search operations for users.
 */
@Service
@AllArgsConstructor
public class UserService {
    private final RequestMediator mediator;
    private final UserValidator userValidator;

    /**
     * Creates a user.
     *
     * @param request create command
     * @return created user details
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    public UserDetailDto handle(UserCreateCommand request) {
        userValidator.validate(request);
        return mediator.execute(request);
    }

    /**
     * Returns all users.
     *
     * @param request get-all query
     * @return list of user DTOs
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<UserDto> handle(UserGetAllQuery request) {
        return mediator.execute(request);
    }

    /**
     * Returns user details by id.
     *
     * @param request get-by-id query
     * @return user detail DTO
     */
    @PreAuthorize("hasAuthority('ADMIN') || #request.id == authentication.principal.id")
    public UserDetailDto handle(UserGetByIdQuery request) {
        return mediator.execute(request);
    }

    /**
     * Updates a user.
     *
     * @param request update command
     * @return updated user details
     */
    @PreAuthorize("hasAuthority('ADMIN') || #request.id == authentication.principal.id")
    public UserDetailDto handle(UserUpdateCommand request) {
        userValidator.validate(request);
        return mediator.execute(request);
    }

    /**
     * Deletes a user.
     *
     * @param request delete command
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    public void handle(UserDeleteCommand request) {
        mediator.execute(request);
    }

    /**
     * Searches users with pagination.
     *
     * @param request search query
     * @return paginated user result
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    public PaginatedResult<UserDto> handle(UserSearchQuery request) {
        return mediator.execute(request);
    }
}
