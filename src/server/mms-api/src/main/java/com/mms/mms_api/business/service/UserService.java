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

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {
    private final RequestMediator mediator;

    @PreAuthorize("hasAuthority('ADMIN')")
    public UserDetailDto handle(UserCreateCommand request) {
        return mediator.execute(request);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public List<UserDto> handle(UserGetAllQuery request) {
        return mediator.execute(request);
    }

    @PreAuthorize("hasAuthority('ADMIN') || #request.id == authentication.principal.id")
    public UserDetailDto handle(UserGetByIdQuery request) {
        return mediator.execute(request);
    }

    @PreAuthorize("hasAuthority('ADMIN') || #request.id == authentication.principal.id")
    public UserDetailDto handle(UserUpdateCommand request) {
        return mediator.execute(request);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public void handle(UserDeleteCommand request) {
        mediator.execute(request);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public PaginatedResult<UserDto> handle(UserSearchQuery request) {
        return mediator.execute(request);
    }
}
