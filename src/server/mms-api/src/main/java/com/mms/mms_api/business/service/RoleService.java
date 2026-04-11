package com.mms.mms_api.business.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mms.mms_api.business.query.role.RoleGetAllQuery;
import com.mms.mms_api.dto.user.RoleDto;
import com.mms.mms_api.mediator.RequestMediator;

import lombok.AllArgsConstructor;

/**
 * Provides read operations for roles.
 */
@Service
@AllArgsConstructor
public class RoleService {
    private final RequestMediator mediator;

    /**
     * Returns all roles.
     *
     * @param request get-all query
     * @return list of role DTOs
     */
    public List<RoleDto> handle(RoleGetAllQuery request) {
        return mediator.execute(request);
    }
}
