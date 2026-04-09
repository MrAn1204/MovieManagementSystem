package com.mms.mms_api.business.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mms.mms_api.business.query.role.RoleGetAllQuery;
import com.mms.mms_api.dto.user.RoleDto;
import com.mms.mms_api.mediator.RequestMediator;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RoleService {
    private final RequestMediator mediator;

    public List<RoleDto> handle(RoleGetAllQuery request) {
        return mediator.execute(request);
    }
}
